package com.telegroup_ltd.vehicle_reservation.controller;

import com.telegroup_ltd.vehicle_reservation.controller.genericController.GenericHasCompanyIdAndDeletableController;
import com.telegroup_ltd.vehicle_reservation.model.Cost;
import com.telegroup_ltd.vehicle_reservation.repository.CostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "api/cost")
@RestController
@Scope("request")
public class CostController extends GenericHasCompanyIdAndDeletableController<Cost, Integer> {

    private final CostRepository repository;

    @Autowired
    public CostController(CostRepository repo) {
        super(repo);
        repository = repo;
    }
}
