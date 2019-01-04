package com.telegroup_ltd.vehicle_reservation.controller;

import com.telegroup_ltd.vehicle_reservation.controller.genericController.GenericController;
import com.telegroup_ltd.vehicle_reservation.model.Role;
import com.telegroup_ltd.vehicle_reservation.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "role")
@Controller
@Scope("request")
public class RoleController extends GenericController<Role, Integer> {

    private final RoleRepository repository;

    @Autowired
    public RoleController(RoleRepository repo) {
        super(repo);
        repository = repo;
    }
}
