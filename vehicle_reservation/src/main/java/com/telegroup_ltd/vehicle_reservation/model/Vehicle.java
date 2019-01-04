package com.telegroup_ltd.vehicle_reservation.model;

import com.telegroup_ltd.vehicle_reservation.common.interfaces.Deletable;
import com.telegroup_ltd.vehicle_reservation.common.interfaces.HasCompanyId;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Vehicle implements Deletable, HasCompanyId {
    private Integer id;
    private String model;
    private String registration;
    private String description;
    private Byte deleted;
    private Integer locationId;
    private Integer companyId;
    private Integer manufacturerId;
    private Integer fuelId;

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
    @Column(name = "model", nullable = false, length = 128)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Basic
    @Column(name = "registration", nullable = false, length = 128)
    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
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
    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }

    @Basic
    @Column(name = "location_id", nullable = false)
    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
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
    @Column(name = "manufacturer_id", nullable = false)
    public Integer getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    @Basic
    @Column(name = "fuel_id", nullable = false)
    public Integer getFuelId() {
        return fuelId;
    }

    public void setFuelId(Integer fuelId) {
        this.fuelId = fuelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(id, vehicle.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", registration='" + registration + '\'' +
                ", description='" + description + '\'' +
                ", deleted=" + deleted +
                ", locationId=" + locationId +
                ", companyId=" + companyId +
                ", manufacturerId=" + manufacturerId +
                ", fuelId=" + fuelId +
                '}';
    }

    public Vehicle() {
    }

    public Vehicle(Integer id, String model, String registration, String description,
                   Byte deleted, Integer locationId, Integer companyId,
                   Integer manufacturerId, Integer fuelId) {
        this.id = id;
        this.model = model;
        this.registration = registration;
        this.description = description;
        this.deleted = deleted;
        this.locationId = locationId;
        this.companyId = companyId;
        this.manufacturerId = manufacturerId;
        this.fuelId = fuelId;
    }
}
