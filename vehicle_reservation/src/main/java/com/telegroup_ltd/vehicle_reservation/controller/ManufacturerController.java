package com.telegroup_ltd.vehicle_reservation.controller;

import com.telegroup_ltd.vehicle_reservation.controller.genericController.GenericController;
import com.telegroup_ltd.vehicle_reservation.model.Manufacturer;
import com.telegroup_ltd.vehicle_reservation.repository.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "api/manufacturer")
@RestController
@Scope("request")
public class ManufacturerController extends GenericController<Manufacturer, Integer> {

    private final ManufacturerRepository repository;

    @Autowired
    public ManufacturerController(ManufacturerRepository repo) {
        super(repo);
        repository = repo;
    }
}
