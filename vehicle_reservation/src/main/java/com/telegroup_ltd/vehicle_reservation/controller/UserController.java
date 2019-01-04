package com.telegroup_ltd.vehicle_reservation.controller;

import com.telegroup_ltd.vehicle_reservation.controller.genericController.GenericHasCompanyIdAndDeletableController;
import com.telegroup_ltd.vehicle_reservation.model.User;
import com.telegroup_ltd.vehicle_reservation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "user")
@Controller
@Scope("request")
public class UserController extends GenericHasCompanyIdAndDeletableController<User, Integer> {

    private final UserRepository repository;

    @Autowired
    public UserController(UserRepository repo) {
        super(repo);
        repository = repo;
    }
}
