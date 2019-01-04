package com.telegroup_ltd.vehicle_reservation.model;

import com.telegroup_ltd.vehicle_reservation.common.interfaces.Deletable;
import com.telegroup_ltd.vehicle_reservation.common.interfaces.HasCompanyId;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Location implements Deletable, HasCompanyId {
    private Integer id;
    private String name;
    private Double lat;
    private Double lng;
    private Byte deleted;
    private Integer companyId;

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
    @Column(name = "name", nullable = false, length = 128)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "lat", nullable = false, precision = 0)
    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    @Basic
    @Column(name = "lng", nullable = false, precision = 0)
    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
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
    @Column(name = "company_id", nullable = false)
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
        Location location = (Location) o;
        return Objects.equals(id, location.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", deleted=" + deleted +
                ", companyId=" + companyId +
                '}';
    }

    public Location() {
    }

    public Location(Integer id, String name, Double lat, Double lng, Byte deleted,
                    Integer companyId) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.deleted = deleted;
        this.companyId = companyId;
    }
}
