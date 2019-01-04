package com.telegroup_ltd.vehicle_reservation.controller;

import com.telegroup_ltd.vehicle_reservation.controller.genericController.GenericController;
import com.telegroup_ltd.vehicle_reservation.model.CostType;
import com.telegroup_ltd.vehicle_reservation.repository.CostTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "cost-type")
@Controller
@Scope("request")
public class CostTypeController extends GenericController<CostType, Integer> {

    private final CostTypeRepository repository;

    @Autowired
    public CostTypeController(CostTypeRepository repo) {
        super(repo);
        repository = repo;
    }
}
