package com.telegroup_ltd.vehicle_reservation.repository.repositoryCustom.repositoryImpl;

import com.telegroup_ltd.vehicle_reservation.model.modelCustom.LoggerUser;
import com.telegroup_ltd.vehicle_reservation.repository.repositoryCustom.LoggerRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class LoggerRepositoryImpl implements LoggerRepositoryCustom {

    private static final String SQL_GET_EXTENDED_ALL = "SELECT l.id, l.action_type, l.action_details, l.table_name, l.created, l.user_id, l.atomic, l.company_id, u.username FROM logger l JOIN user u ON l.user_id=u.id ORDER BY l.created DESC";
    private static final String SQL_GET_EXTENDED_BY_COMPANY_ID = "SELECT l.id, l.action_type, l.action_details, l.table_name, l.created, l.user_id, l.atomic, l.company_id, u.username FROM logger l JOIN user u ON l.user_id=u.id WHERE l.company_id=? ORDER BY l.created DESC";

    @PersistenceContext
    private EntityManager entityManager;

    public List<LoggerUser> getExtendedAll(){
        return entityManager.createNativeQuery(SQL_GET_EXTENDED_ALL, "LoggerUserMapping").getResultList();
    }

    public List<LoggerUser> getExtendedByCompanyId(Integer id){
        return entityManager.createNativeQuery(SQL_GET_EXTENDED_BY_COMPANY_ID, "LoggerUserMapping").setParameter(1, id).getResultList();
    }
}
