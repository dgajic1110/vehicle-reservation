package com.telegroup_ltd.vehicle_reservation.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Vehicle {
    private int id;
    private String model;
    private String registration;
    private String description;
    private int locationId;
    private int companyId;
    private int manufacturerId;
    private int fuelId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
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
    @Column(name = "location_id", nullable = false)
    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
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
    @Column(name = "manufacturer_id", nullable = false)
    public int getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    @Basic
    @Column(name = "fuel_id", nullable = false)
    public int getFuelId() {
        return fuelId;
    }

    public void setFuelId(int fuelId) {
        this.fuelId = fuelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return id == vehicle.id &&
                locationId == vehicle.locationId &&
                companyId == vehicle.companyId &&
                manufacturerId == vehicle.manufacturerId &&
                fuelId == vehicle.fuelId &&
                Objects.equals(model, vehicle.model) &&
                Objects.equals(registration, vehicle.registration) &&
                Objects.equals(description, vehicle.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, registration, description, locationId, companyId, manufacturerId, fuelId);
    }
}
