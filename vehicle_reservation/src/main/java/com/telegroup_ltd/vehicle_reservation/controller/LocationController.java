package com.telegroup_ltd.vehicle_reservation.controller;

import com.telegroup_ltd.vehicle_reservation.controller.genericController.GenericHasCompanyIdAndDeletableController;
import com.telegroup_ltd.vehicle_reservation.model.Location;
import com.telegroup_ltd.vehicle_reservation.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(value = "location")
@Controller
@Scope("request")
public class LocationController extends GenericHasCompanyIdAndDeletableController<Location, Integer> {

    private final LocationRepository repository;

    @Autowired
    public LocationController(LocationRepository repo) {
        super(repo);
        repository = repo;
    }

    @RequestMapping("/byCompany/{id}")
    public List<Location> getByCompany(@PathVariable Integer id){
        return repository.getAllByCompanyIdAndDeletedIs(id, (byte)0);
    }
}
