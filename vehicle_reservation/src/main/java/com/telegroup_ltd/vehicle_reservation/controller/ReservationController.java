package com.telegroup_ltd.vehicle_reservation.controller;

import com.telegroup_ltd.vehicle_reservation.controller.genericController.GenericHasCompanyIdAndDeletableController;
import com.telegroup_ltd.vehicle_reservation.model.Reservation;
import com.telegroup_ltd.vehicle_reservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "api/reservation")
@RestController
@Scope("request")
public class ReservationController extends GenericHasCompanyIdAndDeletableController<Reservation, Integer> {

    private final ReservationRepository repository;

    @Autowired
    public ReservationController(ReservationRepository repo) {
        super(repo);
        repository = repo;
    }
}
