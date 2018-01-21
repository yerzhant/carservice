package example.vehicleworkshop.vehiclecatalog.domain;

import example.vehicleworkshop.publishedlanguage.VehicleData;
import example.vehicleworkshop.publishedlanguage.VehicleIdentificationNumber;
import example.vehicleworkshop.sharedkernel.BaseEntity;
import lombok.Getter;

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