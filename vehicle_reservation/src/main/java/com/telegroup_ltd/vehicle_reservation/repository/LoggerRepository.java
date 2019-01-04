package com.telegroup_ltd.vehicle_reservation.repository;

import com.telegroup_ltd.vehicle_reservation.model.Logger;
import com.telegroup_ltd.vehicle_reservation.repository.repositoryCustom.LoggerRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoggerRepository extends JpaRepository<Logger, Integer>, LoggerRepositoryCustom {

}
