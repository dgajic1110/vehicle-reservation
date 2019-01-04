package com.telegroup_ltd.vehicle_reservation.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Cost {
    private int id;
    private BigDecimal value;
    private Timestamp date;
    private String description;
    private byte deleted;
    private int costTypeId;
    private int companyId;
    private Integer vehicleId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "value", nullable = false, precision = 2)
    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Basic
    @Column(name = "date", nullable = false)
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "deleted", nullable = false)
    public byte getDeleted() {
        return deleted;
    }

    public void setDeleted(byte deleted) {
        this.deleted = deleted;
    }

    @Basic
    @Column(name = "cost_type_id", nullable = false)
    public int getCostTypeId() {
        return costTypeId;
    }

    public void setCostTypeId(int costTypeId) {
        this.costTypeId = costTypeId;
    }

    @Basic
    @Column(name = "company_id", nullable = false)
    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    @Basic
    @Column(name = "vehicle_id", nullable = true)
    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cost cost = (Cost) o;
        return id == cost.id &&
                deleted == cost.deleted &&
                costTypeId == cost.costTypeId &&
                companyId == cost.companyId &&
                Objects.equals(value, cost.value) &&
                Objects.equals(date, cost.date) &&
                Objects.equals(description, cost.description) &&
                Objects.equals(vehicleId, cost.vehicleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, date, description, deleted, costTypeId, companyId, vehicleId);
    }
}
