package com.telegroup_ltd.vehicle_reservation.repository;

import com.telegroup_ltd.vehicle_reservation.common.interfaces.HasCompanyIdAndDeletableRepository;
import com.telegroup_ltd.vehicle_reservation.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer>,
        HasCompanyIdAndDeletableRepository<Vehicle> {

    Vehicle findByRegistrationAndDeleted(String registration, Byte deleted);
    List<Vehicle> getAllByLocationIdAndCompanyIdAndDeletedIs(Integer locationId, Integer companyId, Byte deleted);
}
