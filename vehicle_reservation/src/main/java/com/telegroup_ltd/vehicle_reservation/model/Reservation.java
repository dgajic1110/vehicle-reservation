package com.telegroup_ltd.vehicle_reservation.model;

import com.telegroup_ltd.vehicle_reservation.common.interfaces.Deletable;
import com.telegroup_ltd.vehicle_reservation.common.interfaces.HasCompanyId;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Reservation implements Deletable, HasCompanyId {
    private Integer id;
    private Timestamp startDate;
    private Timestamp endDate;
    private Integer startMileage;
    private Integer endMileage;
    private String direction;
    private Byte deleted;
    private Integer userId;
    private Integer companyId;
    private Integer vehicleId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
    public Integer getStartMileage() {
        return startMileage;
    }

    public void setStartMileage(Integer startMileage) {
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
    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }

    @Basic
    @Column(name = "user_id", nullable = false)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "company_id", nullable = false)
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Basic
    @Column(name = "vehicle_id", nullable = false)
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
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", startMileage=" + startMileage +
                ", endMileage=" + endMileage +
                ", direction='" + direction + '\'' +
                ", deleted=" + deleted +
                ", userId=" + userId +
                ", companyId=" + companyId +
                ", vehicleId=" + vehicleId +
                '}';
    }

    public Reservation() {
    }

    public Reservation(Integer id, Timestamp startDate, Timestamp endDate,
                       Integer startMileage, Integer endMileage, String direction,
                       Byte deleted, Integer userId, Integer companyId, Integer vehicleId) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startMileage = startMileage;
        this.endMileage = endMileage;
        this.direction = direction;
        this.deleted = deleted;
        this.userId = userId;
        this.companyId = companyId;
        this.vehicleId = vehicleId;
    }
}
