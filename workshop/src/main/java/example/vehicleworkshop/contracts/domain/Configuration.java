package example.vehicleworkshop.contracts.domain;


import example.vehicleworkshop.client.domain.ClientServiceFacade;
import example.vehicleworkshop.repairscatalog.domain.RepairsCatalogServiceFacade;
import example.vehicleworkshop.vehiclecatalog.domain.VehicleServiceFacade;

class Configuration {

    ContractServiceFacade contractServiceFacade(ClientServiceFacade clientServiceFacade,
                                                VehicleServiceFacade vehicleServiceFacade,
                                                RepairsCatalogServiceFacade repairsCatalogServiceFacade) {

        return new ContractServiceFacade(clientServiceFacade, vehicleServiceFacade, repairsCatalogServiceFacade,
                new ContractRepositoryInMemoryImpl(), new ContractNumberGenerator(), new ContractMapper());
    }

}
