package com.telegroup_ltd.vehicle_reservation.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Logger {
    private int id;
    private String actionType;
    private String actionDetails;
    private String tableName;
    private Timestamp created;
    private int userId;
    private byte atomic;
    private Integer companyId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "action_type", nullable = false, length = 128)
    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    @Basic
    @Column(name = "action_details", nullable = false, length = -1)
    public String getActionDetails() {
        return actionDetails;
    }

    public void setActionDetails(String actionDetails) {
        this.actionDetails = actionDetails;
    }

    @Basic
    @Column(name = "table_name", nullable = false, length = 128)
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Basic
    @Column(name = "created", nullable = false)
    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Basic
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "atomic", nullable = false)
    public byte getAtomic() {
        return atomic;
    }

    public void setAtomic(byte atomic) {
        this.atomic = atomic;
    }

    @Basic
    @Column(name = "company_id", nullable = true)
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Logger logger = (Logger) o;
        return id == logger.id &&
                userId == logger.userId &&
                atomic == logger.atomic &&
                Objects.equals(actionType, logger.actionType) &&
                Objects.equals(actionDetails, logger.actionDetails) &&
                Objects.equals(tableName, logger.tableName) &&
                Objects.equals(created, logger.created) &&
                Objects.equals(companyId, logger.companyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, actionType, actionDetails, tableName, created, userId, atomic, companyId);
    }
}
