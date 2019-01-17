package com.telegroup_ltd.vehicle_reservation.repository.repositoryCustom.repositoryImpl;

import com.telegroup_ltd.vehicle_reservation.model.modelCustom.LoggerCompanyUserRole;
import com.telegroup_ltd.vehicle_reservation.repository.repositoryCustom.LoggerRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class LoggerRepositoryImpl implements LoggerRepositoryCustom {

    private static final String SQL_GET_EXTENDED_ALL = "SELECT l.id, l.action_type, l.action_details, l.table_name," +
            " l.created, l.user_id, l.atomic, l.company_id, c.name as company_name, u.username, r.name as role_name" +
            " FROM logger l inner join user u ON l.user_id=u.id inner join role r on u.role_id = r.id" +
            " left join company c on l.company_id = c.id ORDER BY l.created DESC";
    private static final String SQL_GET_EXTENDED_BY_COMPANY_ID = "SELECT l.id, l.action_type, l.action_details," +
            " l.table_name, l.created, l.user_id, l.atomic, l.company_id, c.name as company_name, u.username," +
            " r.name as role_name FROM logger l inner join user u ON l.user_id=u.id inner join role r on u.role_id = r.id " +
            "left join company c on l.company_id = c.id WHERE l.company_id=? ORDER BY l.created DESC";

    @PersistenceContext
    private EntityManager entityManager;

    public List<LoggerCompanyUserRole> getExtendedAll(){
        return entityManager.createNativeQuery(SQL_GET_EXTENDED_ALL, "LoggerCompanyUserRoleMapping").getResultList();
    }

    public List<LoggerCompanyUserRole> getExtendedByCompanyId(Integer id){
        return entityManager.createNativeQuery(SQL_GET_EXTENDED_BY_COMPANY_ID, "LoggerCompanyUserRoleMapping").setParameter(1, id).getResultList();
    }
}
