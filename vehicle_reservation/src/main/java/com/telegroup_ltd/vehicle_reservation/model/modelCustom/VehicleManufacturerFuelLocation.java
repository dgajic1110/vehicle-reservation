package com.telegroup_ltd.vehicle_reservation.model.modelCustom;

import com.telegroup_ltd.vehicle_reservation.model.Vehicle;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.MappedSuperclass;
import javax.persistence.SqlResultSetMapping;

@SuppressWarnings("WeakerAccess")
@SqlResultSetMapping(
        name = "VehicleManufacturerFuelLocationMapping",
        classes = @ConstructorResult(
                targetClass = VehicleManufacturerFuelLocation.class,
                columns = {
                        @ColumnResult(name = "id", type = Integer.class),
                        @ColumnResult(name = "model", type = String.class),
                        @ColumnResult(name = "registration", type = String.class),
                        @ColumnResult(name = "description", type = String.class),
                        @ColumnResult(name = "deleted", type = Byte.class),
                        @ColumnResult(name = "location_id", type = Integer.class),
                        @ColumnResult(name = "company_id", type = Integer.class),
                        @ColumnResult(name = "manufacturer_id", type = Integer.class),
                        @ColumnResult(name = "fuel_id", type = Integer.class),
                        @ColumnResult(name = "manufacturer_name", type = String.class),
                        @ColumnResult(name = "fuel_name", type = String.class),
                        @ColumnResult(name = "location_name", type = String.class)
                }
        )
)
@MappedSuperclass
public class VehicleManufacturerFuelLocation extends Vehicle {

    private String manufacturerName;
    private String fuelName;
    private String locationName;

    public VehicleManufacturerFuelLocation() {
    }

    @SuppressWarnings("WeakerAccess")
    public VehicleManufacturerFuelLocation(Integer id, String model, String registration, String description,
                           Byte deleted, Integer locationId, Integer companyId, Integer manufacturerId,
                           Integer fuelId, String manufacturerName, String fuelName, String locationName) {
        super(id, model, registration, description, deleted, locationId, companyId, manufacturerId, fuelId);
        this.manufacturerName = manufacturerName;
        this.fuelName = fuelName;
        this.locationName = locationName;
    }

    @SuppressWarnings("WeakerAccess")
    public VehicleManufacturerFuelLocation(Vehicle vehicle, String manufacturerName,
                                           String fuelName, String locationName) {
        super(vehicle.getId(), vehicle.getModel(), vehicle.getRegistration(),
                vehicle.getDescription(), vehicle.getDeleted(), vehicle.getLocationId(),
                vehicle.getCompanyId(), vehicle.getManufacturerId(), vehicle.getFuelId());
        this.manufacturerName = manufacturerName;
        this.fuelName = fuelName;
        this.locationName = locationName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getFuelName() {
        return fuelName;
    }

    public void setFuelName(String fuelName) {
        this.fuelName = fuelName;
    }
}
