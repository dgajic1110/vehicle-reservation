package com.telegroup_ltd.vehicle_reservation.repository;

import com.telegroup_ltd.vehicle_reservation.common.interfaces.HasCompanyIdAndDeletableRepository;
import com.telegroup_ltd.vehicle_reservation.model.Cost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostRepository extends JpaRepository<Cost, Integer>, HasCompanyIdAndDeletableRepository<Cost> {
}
