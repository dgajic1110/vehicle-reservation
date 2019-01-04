package com.telegroup_ltd.vehicle_reservation.repository.repositoryCustom;

import com.telegroup_ltd.vehicle_reservation.model.modelCustom.LoggerUser;

import java.util.List;

public interface LoggerRepositoryCustom {

    List<LoggerUser> getExtendedAll();
    List<LoggerUser> getExtendedByCompanyId(Integer id);
}
