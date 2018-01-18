package pl.beck.vehicleworkshop.vehiclecatalog.domain;

class Configuration {

    VehicleServiceFacade vehicleServiceFacade() {
        return new VehicleServiceFacade(new VehicleRepositoryInMemoryImpl());
    }
}
