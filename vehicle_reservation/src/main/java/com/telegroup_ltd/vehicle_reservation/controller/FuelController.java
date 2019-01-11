package com.telegroup_ltd.vehicle_reservation.controller;

import com.telegroup_ltd.vehicle_reservation.controller.genericController.GenericController;
import com.telegroup_ltd.vehicle_reservation.model.Fuel;
import com.telegroup_ltd.vehicle_reservation.repository.FuelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "api/fuel")
@RestController
@Scope("request")
public class FuelController extends GenericController<Fuel, Integer> {

    private final FuelRepository repository;

    @Autowired
    public FuelController(FuelRepository repo) {
        super(repo);
        repository = repo;
    }
}
