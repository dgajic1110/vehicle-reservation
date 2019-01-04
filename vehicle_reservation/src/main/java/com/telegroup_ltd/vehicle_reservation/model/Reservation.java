package com.telegroup_ltd.vehicle_reservation.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Reservation {
    private int id;
    private Timestamp startDate;
    private Timestamp endDate;
    private int startMileage;
    private Integer endMileage;
    private String direction;
    private byte deleted;
    private int userId;
    private int companyId;
    private int vehicleId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "start_date", nullable = false)
    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "end_date", nullable = false)
    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "start_mileage", nullable = false)
    public int getStartMileage() {
        return startMileage;
    }

    public void setStartMileage(int startMileage) {
        this.startMileage = startMileage;
    }

    @Basic
    @Column(name = "end_mileage", nullable = true)
    public Integer getEndMileage() {
        return endMileage;
    }

    public void setEndMileage(Integer endMileage) {
        this.endMileage = endMileage;
    }

    @Basic
    @Column(name = "direction", nullable = false, length = 255)
    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
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
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
    @Column(name = "vehicle_id", nullable = false)
    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return id == that.id &&
                startMileage == that.startMileage &&
                deleted == that.deleted &&
                userId == that.userId &&
                companyId == that.companyId &&
                vehicleId == that.vehicleId &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(endMileage, that.endMileage) &&
                Objects.equals(direction, that.direction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, endDate, startMileage, endMileage, direction, deleted, userId, companyId, vehicleId);
    }
}
