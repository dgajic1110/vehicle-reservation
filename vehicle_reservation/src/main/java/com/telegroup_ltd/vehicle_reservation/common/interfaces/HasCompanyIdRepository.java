package com.telegroup_ltd.vehicle_reservation.common.interfaces;

import java.util.List;

public interface HasCompanyIdRepository<T extends HasCompanyId> {

    List<T> getAllByCompanyId(Integer companyId);
}