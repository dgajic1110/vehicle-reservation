package com.telegroup_ltd.vehicle_reservation.controller;

import com.telegroup_ltd.vehicle_reservation.controller.genericController.GenericDeletableController;
import com.telegroup_ltd.vehicle_reservation.model.Company;
import com.telegroup_ltd.vehicle_reservation.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "company")
@Controller
@Scope("request")
public class CompanyController extends GenericDeletableController<Company, Integer> {

    private final CompanyRepository repository;

    @Autowired
    public CompanyController(CompanyRepository repo) {
        super(repo);
        repository = repo;
    }
}
