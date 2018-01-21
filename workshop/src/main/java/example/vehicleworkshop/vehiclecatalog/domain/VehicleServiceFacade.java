package example.vehicleworkshop.vehiclecatalog.domain;

import example.vehicleworkshop.publishedlanguage.VehicleData;
import example.vehicleworkshop.publishedlanguage.VehicleIdentificationNumber;
import example.vehicleworkshop.vehiclecatalog.domain.commandmodel.AddNewVehicleRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class VehicleServiceFacade {

    private final VehicleRepository vehicleRepository;

    VehicleData addNewVehicleToSystem(AddNewVehicleRequestDto addNewVehicleRequest) {

        log.info("Add new vehicle to system{}", addNewVehicleRequest);

        String brand = addNewVehicleRequest.getBrand();
        String model = addNewVehicleRequest.getModel();
        String engineType = addNewVehicleRequest.getEngineType();
        double engineCapacityValue = addNewVehicleRequest.getEngineCapacity();
        String engineCapacityUnitName = addNewVehicleRequest.getEngineCapacityUnitName();

        EngineCapacity engineCapacity = new EngineCapacity(engineCapacityValue, engineCapacityUnitName);
        VehicleDetails vehicleDetails = new VehicleDetails(brand, model, engineType, engineCapacity);

        String vinValue = addNewVehicleRequest.getVin();
        VehicleIdentificationNumber vin = new VehicleIdentificationNumber(vinValue);

        final Vehicle newVehicle = new Vehicle(vin, vehicleDetails);

        vehicleRepository.save(newVehicle);

        return newVehicle.getSnapshot();
    }

    public VehicleData fetchVehicleData(String vin) {
        log.info("Fetch vehicle data by vin{}", vin);
        Vehicle vehicle = vehicleRepository.findByVinOrThrow(vin);
        return vehicle.getSnapshot();
    }

}
