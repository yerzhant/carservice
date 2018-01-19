package pl.beck.vehicleworkshop.vehiclecatalog.domain;

import lombok.Getter;
import pl.beck.vehicleworkshop.publishedlanguage.VehicleIdentificationNumber;
import pl.beck.vehicleworkshop.sharedkernel.BaseEntity;
import pl.beck.vehicleworkshop.publishedlanguage.VehicleData;

@Getter
class Vehicle extends BaseEntity {

    private Long id;

    private final VehicleIdentificationNumber vin;

    private VehicleDetails vehicleDetails;

    Vehicle(VehicleIdentificationNumber vin, VehicleDetails vehicleDetails) {
        this.vin = vin;
        this.vehicleDetails = vehicleDetails;
    }

    VehicleData getSnapshot() {
        return new VehicleData(vin, vehicleDetails.getBrand(), vehicleDetails.getModel(), vehicleDetails.getEngineType().name());
    }
}


//TODO add implementation extends BaseEntity, fields etc. Car - better name?