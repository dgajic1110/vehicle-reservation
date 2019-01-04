package com.telegroup_ltd.vehicle_reservation.repository;

import com.telegroup_ltd.vehicle_reservation.model.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationTypeRepository extends JpaRepository<NotificationType, Integer> {
}
