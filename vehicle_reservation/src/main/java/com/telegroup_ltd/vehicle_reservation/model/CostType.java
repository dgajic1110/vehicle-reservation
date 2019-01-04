package com.telegroup_ltd.vehicle_reservation.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cost_type", schema = "vehicle_reservation", catalog = "")
public class CostType {
    private Integer id;
    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CostType costType = (CostType) o;
        return Objects.equals(id, costType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
