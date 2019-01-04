package com.telegroup_ltd.vehicle_reservation.common.interfaces;

import java.util.List;

public interface DeletableRepository<T extends Deletable> {

    List<T> getAllByDeletedIs(Byte deleted);
}
