package com.telegroup_ltd.vehicle_reservation.repository;

import com.telegroup_ltd.vehicle_reservation.common.interfaces.HasCompanyIdAndDeletableRepository;
import com.telegroup_ltd.vehicle_reservation.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Integer>, HasCompanyIdAndDeletableRepository<Location> {
}
