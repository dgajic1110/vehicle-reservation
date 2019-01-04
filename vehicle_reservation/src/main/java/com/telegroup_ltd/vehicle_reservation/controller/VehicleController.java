package com.telegroup_ltd.vehicle_reservation.controller;

import com.telegroup_ltd.vehicle_reservation.controller.genericController.GenericHasCompanyIdAndDeletableController;
import com.telegroup_ltd.vehicle_reservation.model.Vehicle;
import com.telegroup_ltd.vehicle_reservation.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "vehicle")
@Controller
@Scope("request")
public class VehicleController extends GenericHasCompanyIdAndDeletableController<Vehicle, Integer> {

    private final VehicleRepository repository;

    @Autowired
    public VehicleController(VehicleRepository repo) {
        super(repo);
        repository = repo;
    }
}
