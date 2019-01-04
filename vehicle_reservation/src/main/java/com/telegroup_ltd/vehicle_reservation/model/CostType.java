package com.telegroup_ltd.vehicle_reservation.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cost_type", schema = "vehicle_reservation", catalog = "")
public class CostType {
    private int id;
    private String name;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CostType costType = (CostType) o;
        return id == costType.id &&
                Objects.equals(name, costType.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
