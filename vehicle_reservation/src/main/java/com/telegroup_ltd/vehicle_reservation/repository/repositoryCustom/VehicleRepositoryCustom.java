package com.telegroup_ltd.vehicle_reservation.repository.repositoryCustom;

import com.telegroup_ltd.vehicle_reservation.model.modelCustom.VehicleManufacturerFuelLocation;

import java.util.List;

public interface VehicleRepositoryCustom {

    List<VehicleManufacturerFuelLocation> getAllExtendedByCompanyIdAndDeleted(Integer companyId, Byte deleted);
}
