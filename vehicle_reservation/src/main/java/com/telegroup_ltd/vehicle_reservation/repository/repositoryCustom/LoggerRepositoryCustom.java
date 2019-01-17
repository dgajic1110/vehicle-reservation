package com.telegroup_ltd.vehicle_reservation.repository.repositoryCustom;

import com.telegroup_ltd.vehicle_reservation.model.modelCustom.LoggerCompanyUserRole;

import java.util.List;

public interface LoggerRepositoryCustom {

    List<LoggerCompanyUserRole> getExtendedAll();
    List<LoggerCompanyUserRole> getExtendedByCompanyId(Integer id);
}
