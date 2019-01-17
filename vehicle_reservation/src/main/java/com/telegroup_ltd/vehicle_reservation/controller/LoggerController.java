package com.telegroup_ltd.vehicle_reservation.controller;

import com.telegroup_ltd.vehicle_reservation.controller.genericController.GenericController;
import com.telegroup_ltd.vehicle_reservation.model.Logger;
import com.telegroup_ltd.vehicle_reservation.model.User;
import com.telegroup_ltd.vehicle_reservation.model.modelCustom.LoggerCompanyUserRole;
import com.telegroup_ltd.vehicle_reservation.repository.LoggerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "api/logger")
@RestController
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
    public List getAll() {
        User user = userBean.getUser();
        if(user.getRoleId().equals(roleSystemAdmin))
            return repository.getExtendedAll();
        return repository.getExtendedByCompanyId(userBean.getUser().getCompanyId());
    }

}
