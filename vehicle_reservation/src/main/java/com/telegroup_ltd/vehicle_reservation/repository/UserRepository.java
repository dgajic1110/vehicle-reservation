package com.telegroup_ltd.vehicle_reservation.repository;

import com.telegroup_ltd.vehicle_reservation.common.interfaces.HasCompanyIdAndDeletableRepository;
import com.telegroup_ltd.vehicle_reservation.model.User;
import com.telegroup_ltd.vehicle_reservation.repository.repositoryCustom.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>, UserRepositoryCustom,
        HasCompanyIdAndDeletableRepository<User> {

    User getByToken(String token);
    User getByEmail(String email);
    User getByUsername(String username);
    List<User> getAllByEmail(String email);
    List<User> getAllByUsername(String username);
    List<User> getAllByCompanyIdAndActiveAndDeleted(Integer companyId, Byte active, Byte deleted);

}
