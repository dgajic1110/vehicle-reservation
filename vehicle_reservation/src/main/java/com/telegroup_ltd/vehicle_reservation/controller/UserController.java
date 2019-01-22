package com.telegroup_ltd.vehicle_reservation.controller;

import com.telegroup_ltd.vehicle_reservation.common.exceptions.BadRequestException;
import com.telegroup_ltd.vehicle_reservation.common.exceptions.ForbiddenException;
import com.telegroup_ltd.vehicle_reservation.controller.genericController.GenericHasCompanyIdAndDeletableController;
import com.telegroup_ltd.vehicle_reservation.model.LoginInfo;
import com.telegroup_ltd.vehicle_reservation.model.PasswordInfo;
import com.telegroup_ltd.vehicle_reservation.model.User;
import com.telegroup_ltd.vehicle_reservation.repository.CompanyRepository;
import com.telegroup_ltd.vehicle_reservation.repository.UserRepository;
import com.telegroup_ltd.vehicle_reservation.util.Notification;
import com.telegroup_ltd.vehicle_reservation.util.Util;
import com.telegroup_ltd.vehicle_reservation.util.Validator;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/user")
@Scope("request")
public class UserController extends GenericHasCompanyIdAndDeletableController<User, Integer> {

    private final UserRepository repository;
    private final CompanyRepository companyRepository;
    private final Notification notification;

    @Value(value = "${role.system_admin}")
    private Integer roleSystemAdministrator;
    @Value(value = "${notification.all}")
    private Integer notificationAll;
    @Value(value = "${badRequest.delete}")
    private String badRequestDelete;
    @Value(value = "${password.length}")
    private Integer passwordLength;

    @Autowired
    public UserController(UserRepository repo, CompanyRepository companyRepository, Notification notification) {
        super(repo);
        repository = repo;
        this.companyRepository = companyRepository;
        this. notification = notification;
    }

    @Override
    public List getAll() throws ForbiddenException {
        return repository.getExtendedByCompanyIdAndActiveAndDeleted(userBean.getUser().getCompanyId(),(byte) 1, (byte) 0);
    }


    @Transactional
    @Override
    public User insert(@RequestBody User object) throws BadRequestException, ForbiddenException {
        if (!roleSystemAdministrator.equals(object.getRoleId()))
            object.setNotificationTypeId(notificationAll);
        for(User sameEmailUser : repository.getAllByEmail(object.getEmail())){
            if (Util.bothNullOrEqual(object.getCompanyId(),sameEmailUser.getCompanyId())
                    && sameEmailUser.getActive().equals((byte) 1))
                throw new BadRequestException("E-mail već postoji!");
        }
        object.setToken(RandomStringUtils.randomAlphanumeric(64));
        notification.sendInvite(object.getEmail(),object.getToken());
        return super.insert(object);
//        User user = super.insert(object);
//        Company company = companyController.findById(user.getCompanyId());
//        String locationName=locationController.findById(company.getLocationId()).getName();
//        return new UserLocation(user,locationName);

    }

    @Override
    @Transactional
    public String update(@PathVariable Integer id, @RequestBody User user) throws BadRequestException {
        if(userBean.getUser().getId().equals(id)){
            User userTemp = repository.findById(id).orElse(null);
            User oldUser = cloner.deepClone(repository.findById(id).orElse(null));
            userTemp.setFirstName(user.getFirstName());
            userTemp.setLastName(user.getLastName());
            logUpdateAction(user, oldUser);
            return "Success";
        }
        throw new BadRequestException("Izmjena nije moguća");
    }

    @RequestMapping("byCompany/{companyId}")
    public List getByCompanyId(@PathVariable Integer companyId) throws ForbiddenException {
        if (!userBean.getUser().getRoleId().equals(roleSystemAdministrator)
                && !companyId.equals(userBean.getUser().getCompanyId()))
            throw new ForbiddenException("Forbidden");
        return repository.getExtendedByCompanyIdAndActiveAndDeleted(companyId.equals(0) ? null : companyId, (byte) 1, (byte) 0);
    }

    @RequestMapping(value = {"/state"})
    public User checkState() throws ForbiddenException {
        if (userBean.getLoggedIn())
            return userBean.getUser();
        throw new ForbiddenException("Forbidden");
    }

    @RequestMapping(value = "/companyUsers/{companyId}")
    public List getByCompanyIdAndDeleted(@PathVariable Integer companyId) throws BadRequestException {
        return repository.getAllByCompanyIdAndActiveAndDeleted(companyId, (byte)1, (byte)0);
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null)
            session.invalidate();
        return "Success";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public User login(@RequestBody LoginInfo userInformation) throws ForbiddenException {
        System.out.println(userInformation.getUsername() + " " + userInformation.getPassword());
        User user = repository.login(userInformation.getUsername(), userInformation.getPassword(), userInformation.getCompanyName());
        if (user != null) {
            userBean.setLoggedIn(true);
            userBean.setUser(user);
            return user;
        }
        throw new ForbiddenException("Forbidden");
    }

    @Override
    public String delete(@PathVariable Integer id) throws BadRequestException, ForbiddenException {
        User object = findById(id);
        if (object == null)
            throw new BadRequestException(badRequestDelete);
        object.setToken(null);
        object.setDeleted((byte) 1);
        logDeleteAction(object);
        return "Success";
    }


    @RequestMapping(value = "/check/{token}",method = RequestMethod.GET)
    public Integer checkToken(@PathVariable String token) throws ForbiddenException {
        User user=repository.getByToken(token);
        if (user==null || user.getRegistrationDate().toLocalDateTime().plusDays(1).isBefore(LocalDateTime.now()))
            throw new ForbiddenException("Forbidden");
        return user.getId();
    }

    @Transactional
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(@RequestBody User user) throws ForbiddenException, BadRequestException {
        User realUser=repository.findById(user.getId()).orElse(null);
        if (realUser==null)
            throw new ForbiddenException("Forbidden!");
        if (realUser.getRegistrationDate().toLocalDateTime().plusDays(1).isBefore(LocalDateTime.now()))
            throw new ForbiddenException("Token je istekao");
        for(User sameUsernameUser:repository.getAllByUsername(user.getUsername())){
            if (Util.bothNullOrEqual(realUser.getCompanyId(), sameUsernameUser.getCompanyId())
                    && sameUsernameUser.getActive().equals((byte) 1))
                throw new BadRequestException("Korisničko ime već postoji!");
        }
        realUser.setFirstName(user.getFirstName());
        realUser.setLastName(user.getLastName());
        realUser.setUsername(user.getUsername());
        realUser.setPassword(Util.hashPassword(user.getPassword()));
        realUser.setToken(null);
        realUser.setActive((byte) 1);
        return "Success";
    }

    @RequestMapping(value = "/deactivate/{id}", method = RequestMethod.GET)
    public String deactivate(@PathVariable Integer id) throws BadRequestException {
        User user = repository.findById(id).orElse(null);
        if(user != null){
            user.setActive((byte)0);
            if(repository.saveAndFlush(user) != null){
                return "Success";
            }
            throw new BadRequestException("Nemoguća deaktivacija");
        }
        throw new BadRequestException("Nema korisnika");
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public String resetPassword(@RequestBody LoginInfo loginInfo) throws BadRequestException {
        User userTemp = repository.getByUsername(loginInfo.getUsername());
        if (userTemp != null) {
            String companyName = companyRepository.getById(userTemp.getCompanyId()).getName();
            if (companyName != null && loginInfo.getCompanyName() != null
                    && companyName.equals(loginInfo.getCompanyName().trim())) {
                User user = repository.findById(userTemp.getId()).orElse(null);
                String newPassword = Util.randomString(passwordLength);
                user.setPassword(Util.hashPassword(newPassword));
                if(repository.saveAndFlush(user) != null){
                    notification.sendMail(user.getEmail().trim(), "Vehicle Reservation - Resetovanje lozinke",
                            "Vaša nova lozinka je " + newPassword + " .");
                    return "Success";
                }
                throw new BadRequestException("Resetovanje lozinke nije moguće");
            }
            throw new BadRequestException("Ne postoji korisnik");
        }
        throw new BadRequestException("Ne postoji korisnik");
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public String updatePassword(@RequestBody PasswordInfo passwordInfo) throws BadRequestException{
        User user = repository.findById(userBean.getUser().getId()).orElse(null);
        if(user != null){
            if(passwordInfo.getOldPassword() != null && user.getPassword().trim().equals(Util.hashPassword(passwordInfo.getOldPassword().trim()))){
                if(passwordInfo.getNewPassword() != null && Validator.passwordChecking(passwordInfo.getNewPassword())){
                    if(passwordInfo.getRepeatedNewPassword() != null && passwordInfo.getNewPassword().trim().equals(passwordInfo.getRepeatedNewPassword().trim())){
                        user.setPassword(Util.hashPassword(passwordInfo.getNewPassword()));
                        if(repository.saveAndFlush(user) != null){
                            return "Success";
                        }
                        throw new BadRequestException("Izmjena nije moguća");
                    }
                    throw new BadRequestException("Potrebno je ponoviti novu lozinku");
                }
                throw new BadRequestException("Jačina lozinke nije odgovarajuća");
            }
            throw new BadRequestException("Stara lozinka nije tačna");
        }
        throw new BadRequestException("Ne postoji korisnik");
    }

    @RequestMapping(value = "/mailStatus/{id}", method = RequestMethod.POST)
    @Transactional
    public String updateMailStatus(@PathVariable Integer id,
                                   @RequestParam("status") Integer statusId) throws BadRequestException {
        User user = repository.findById(id).orElse(null);
        if(userBean.getUser().getId().equals(id) && user != null){
            User oldObject = user;
            user.setNotificationTypeId(statusId);
            if (repository.saveAndFlush(user) != null) {
                logUpdateAction(user, oldObject);
                return "Success";
            }
            else{
                throw new BadRequestException("Izmjena nije moguća");
            }
        }
        else{
            throw new BadRequestException("Ne postoji korisnik");
        }
    }

    @RequestMapping(value = "/register/{token}", method = RequestMethod.GET)
    public User requestForRegistration(@PathVariable String token) {
        User user = repository.getByToken(token);
        if (user == null) {
            return null;
        }
        if (new Timestamp(System.currentTimeMillis()).before(new Timestamp(user.getRegistrationDate().getTime() + 10 * 60 * 1000))) {
            return user;
        } else {
            return null;
        }
    }

}
