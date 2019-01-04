package com.telegroup_ltd.vehicle_reservation.controller;

import com.telegroup_ltd.vehicle_reservation.common.exceptions.ForbiddenException;
import com.telegroup_ltd.vehicle_reservation.controller.genericController.GenericController;
import com.telegroup_ltd.vehicle_reservation.model.Logger;
import com.telegroup_ltd.vehicle_reservation.model.User;
import com.telegroup_ltd.vehicle_reservation.model.modelCustom.LoggerUser;
import com.telegroup_ltd.vehicle_reservation.repository.LoggerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping(value = "logger")
@Controller
@Scope("request")
public class LoggerController extends GenericController<Logger, Integer> {

    private final LoggerRepository repository;

    @Value(value = "${role.system_admin}")
    private Integer roleSystemAdmin;

    @Autowired
    public LoggerController(LoggerRepository repo) {
        super(repo);
        repository = repo;
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    List getAll() {
        User user = userBean.getUser();
        if(user.getRoleId().equals(roleSystemAdmin))
            return repository.getExtendedAll();
        return repository.getExtendedByCompanyId(userBean.getUser().getCompanyId());
    }

}
