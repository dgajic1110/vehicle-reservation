package com.telegroup_ltd.vehicle_reservation.repository.repositoryCustom.repositoryImpl;

import com.telegroup_ltd.vehicle_reservation.model.User;
import com.telegroup_ltd.vehicle_reservation.model.modelCustom.UserLocation;
import com.telegroup_ltd.vehicle_reservation.repository.repositoryCustom.UserRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class UserRepositoryImpl implements UserRepositoryCustom {

    private static final String SQL_LOGIN_SYSTEM_ADMIN = "SELECT id, username, first_name, last_name, email," +
            "registration_date, active, deleted, token, company_id, role_id, notification_type_id, location_id" +
            " FROM user WHERE username=? AND password=SHA2(?,512) AND company_id is null";
    private static final String SQL_LOGIN = "SELECT u.id, u.username," +
            " u.first_name, u.last_name, u.email, u.registration_date, u.active," +
            " u.deleted, u.company_id, u.role_id, u.notification_type_id, u.location_id" +
            " FROM user u INNER JOIN company c ON u.company_id=c.id" +
            " WHERE u.username=? AND u.password=SHA2(?,512) AND c.name=?";
    private static final String SQL_GET_EXTENDED_SYSTEM_ADMIN = "SELECT u.*, '' as location_name" +
            " FROM user u WHERE company_id is null AND active=? AND deleted=?";
    private static final String SQL_GET_EXTENDED = "SELECT u.*,l.name as location_name" +
            " FROM user u JOIN location l ON u.location_id=l.id WHERE u.company_id=? AND u.active=? AND u.deleted=?";

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public User login(String username, String pwd, String companyName) {
        if("".equals(companyName))
            return (User) entityManager.createNativeQuery(SQL_LOGIN_SYSTEM_ADMIN, "UserMapping")
                    .setParameter(1, username).setParameter(2, pwd).getResultList()
                    .stream().findFirst().orElse(null);
        return (User) entityManager.createNativeQuery(SQL_LOGIN, "UserMapping")
                .setParameter(1, username).setParameter(2, pwd).setParameter(3,companyName)
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public List<UserLocation> getExtendedByCompanyIdAndActiveAndDeleted(Integer companyId, Byte active, Byte deleted) {
        if(companyId == null)
            return (List<UserLocation>) entityManager.createNativeQuery(SQL_GET_EXTENDED_SYSTEM_ADMIN, "UserLocationMapping")
                    .setParameter(1, active).setParameter(2, deleted).getResultList()
                    .stream().findFirst().orElse(null);
        return (List<UserLocation>) entityManager.createNativeQuery(SQL_GET_EXTENDED, "UserLocationMapping")
                .setParameter(1, companyId).setParameter(2, active).setParameter(3, deleted)
                .getResultList().stream().findFirst().orElse(null);
    }
}
