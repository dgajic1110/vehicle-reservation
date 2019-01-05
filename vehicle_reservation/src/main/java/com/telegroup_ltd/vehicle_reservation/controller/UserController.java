package com.telegroup_ltd.vehicle_reservation.controller;

import com.telegroup_ltd.vehicle_reservation.common.exceptions.BadRequestException;
import com.telegroup_ltd.vehicle_reservation.common.exceptions.ForbiddenException;
import com.telegroup_ltd.vehicle_reservation.controller.genericController.GenericHasCompanyIdAndDeletableController;
import com.telegroup_ltd.vehicle_reservation.model.Company;
import com.telegroup_ltd.vehicle_reservation.model.LoginInfo;
import com.telegroup_ltd.vehicle_reservation.model.User;
import com.telegroup_ltd.vehicle_reservation.model.modelCustom.UserLocation;
import com.telegroup_ltd.vehicle_reservation.repository.UserRepository;
import com.telegroup_ltd.vehicle_reservation.util.Notification;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;

@RequestMapping(value = "user")
@Controller
@Scope("request")
public class UserController extends GenericHasCompanyIdAndDeletableController<User, Integer> {

    private final UserRepository repository;
    private final LocationController locationController;
    private final CompanyController companyController;
    private final Notification notification;

    @Value(value = "${role.system_admin}")
    private Integer roleSystemAdministrator;
    @Value(value = "${notification.all}")
    private Integer notificationAll;
    @Value(value = "${badRequest.delete}")
    private String badRequestDelete;

    @Autowired
    public UserController(UserRepository repo, LocationController locationController,
                          CompanyController companyController, Notification notification) {
        super(repo);
        repository = repo;
        this.locationController = locationController;
        this.companyController = companyController;
        this. notification = notification;
    }

    @Override
    public List getAll() throws ForbiddenException {
        return repository.getExtendedByCompanyIdAndActiveAndDeleted(userBean.getUser().getCompanyId(),(byte) 1, (byte) 0);
    }


    @Transactional
    @Override
    public UserLocation insert(@RequestBody User object) throws BadRequestException, ForbiddenException {
        if (!roleSystemAdministrator.equals(object.getRoleId()))
            object.setNotificationTypeId(notificationAll);
        for(User sameEmailUser : repository.getAllByEmail(object.getEmail())){
            if (bothNullOrEqual(object.getCompanyId(),sameEmailUser.getCompanyId())
                    && !sameEmailUser.getActive().equals((byte) 0))
                throw new BadRequestException("E-mail već postoji!");
        }
        object.setToken(RandomStringUtils.randomAlphanumeric(64));
        notification.sendInvite(object.getEmail(),object.getToken());
        User user = super.insert(object);
        Company company = companyController.findById(user.getCompanyId());
        String locationName=locationController.findById(company.getId()).getName();
        return new UserLocation(user,locationName);

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

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null)
            session.invalidate();
        return "Success";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public User login(@RequestBody LoginInfo userInformation) throws ForbiddenException {
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
            if (bothNullOrEqual(realUser.getCompanyId(), sameUsernameUser.getCompanyId())
                    && sameUsernameUser.getActive().equals((byte) 1))
                throw new BadRequestException("Korisničko ime već postoji!");
        }
        realUser.setFirstName(user.getFirstName());
        realUser.setLastName(user.getLastName());
        realUser.setUsername(user.getUsername());
        realUser.setPassword(hashPassword(user.getPassword()));
        realUser.setToken(null);
        realUser.setActive((byte) 1);
        return "Success";
    }

    private boolean bothNullOrEqual(Object first,Object second){
        if (first==null && second ==null)
            return true;
        if (first!=null && first.equals(second))
            return true;
        return false;
    }

    private String hashPassword(String plainText)  {
        MessageDigest digest= null;
        try {
            digest = MessageDigest.getInstance("SHA-512");
            return Hex.encodeHexString(digest.digest(plainText.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
