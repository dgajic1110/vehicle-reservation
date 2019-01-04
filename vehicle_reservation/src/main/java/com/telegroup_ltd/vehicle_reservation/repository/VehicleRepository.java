package com.telegroup_ltd.vehicle_reservation.repository;

import com.telegroup_ltd.vehicle_reservation.common.interfaces.HasCompanyIdAndDeletableRepository;
import com.telegroup_ltd.vehicle_reservation.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer>,
        HasCompanyIdAndDeletableRepository<Vehicle> {
}
