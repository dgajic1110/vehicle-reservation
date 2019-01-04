package com.telegroup_ltd.vehicle_reservation.controller;

import com.telegroup_ltd.vehicle_reservation.controller.genericController.GenericController;
import com.telegroup_ltd.vehicle_reservation.model.NotificationType;
import com.telegroup_ltd.vehicle_reservation.repository.NotificationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "notification-type")
@Controller
@Scope("request")
public class NotificationTypeController extends GenericController<NotificationType, Integer> {

    private final NotificationTypeRepository repository;

    @Autowired
    public NotificationTypeController(NotificationTypeRepository repo) {
        super(repo);
        repository = repo;
    }
}
