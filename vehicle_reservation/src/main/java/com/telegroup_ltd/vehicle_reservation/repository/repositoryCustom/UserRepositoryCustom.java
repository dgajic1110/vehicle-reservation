package com.telegroup_ltd.vehicle_reservation.repository.repositoryCustom;

import com.telegroup_ltd.vehicle_reservation.model.User;
import com.telegroup_ltd.vehicle_reservation.model.modelCustom.UserLocation;

import java.util.List;

public interface UserRepositoryCustom {

    User login(String username, String password, String companyName);
    List<UserLocation> getExtendedByCompanyIdAndActiveAndDeleted(Integer companyId, Byte active, Byte deleted);

}
