package com.telegroup_ltd.vehicle_reservation.controller;

import com.telegroup_ltd.vehicle_reservation.common.exceptions.BadRequestException;
import com.telegroup_ltd.vehicle_reservation.common.exceptions.ForbiddenException;
import com.telegroup_ltd.vehicle_reservation.controller.genericController.GenericHasCompanyIdAndDeletableController;
import com.telegroup_ltd.vehicle_reservation.model.Vehicle;
import com.telegroup_ltd.vehicle_reservation.model.modelCustom.VehicleLocation;
import com.telegroup_ltd.vehicle_reservation.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "api/vehicle")
@RestController
@Scope("request")
public class VehicleController extends GenericHasCompanyIdAndDeletableController<Vehicle, Integer> {

    private final VehicleRepository repository;
    private final LocationController locationController;
    @Value(value = "${badRequest.existingRegistration}")
    private String existingRegistration;

    @Autowired
    public VehicleController(VehicleRepository repo, LocationController locationController) {
        super(repo);
        repository = repo;
        this.locationController = locationController;
    }

    @Override
    public List getAll() throws ForbiddenException {
        List<VehicleLocation> result = new ArrayList<>();
        Map<Integer, String> locations = new HashMap<>();
        for (Vehicle vehicle : super.getAll()) {
            String loc = locations.get(vehicle.getLocationId());
            if(loc == null){
                loc = locationController.findById(vehicle.getLocationId()).getName();
                locations.put(vehicle.getLocationId(), loc);
            }
            result.add(new VehicleLocation(vehicle, loc));
        }
        return result;
    }

    @Override
    @Transactional
    public VehicleLocation insert(@RequestBody Vehicle vehicle) throws BadRequestException, ForbiddenException{
        Vehicle existing = repository.findByRegistrationAndDeleted(vehicle.getRegistration(), (byte) 0);
        if(existing != null)
            throw new BadRequestException(existingRegistration);
        Vehicle inserted = super.insert(vehicle);
        String location = locationController.findById(inserted.getLocationId()).getName();
        return  new VehicleLocation(inserted, location);
    }


    @RequestMapping("/byLocation/{id}")
    public List<Vehicle> getByLocation(@PathVariable Integer id){
        return repository.getAllByLocationIdAndCompanyIdAndDeletedIs(id, userBean.getUser().getCompanyId(), (byte) 0);
    }
}
