package pl.beck.vehicleworkshop.contracts.domain;


import pl.beck.vehicleworkshop.client.domain.ClientServiceFacade;
import pl.beck.vehicleworkshop.repairscatalog.domain.RepairsCatalogServiceFacade;
import pl.beck.vehicleworkshop.vehiclecatalog.domain.VehicleServiceFacade;

class Configuration {

    ContractServiceFacade contractServiceFacade(ClientServiceFacade clientServiceFacade,
                                                VehicleServiceFacade vehicleServiceFacade,
                                                RepairsCatalogServiceFacade repairsCatalogServiceFacade) {

        return new ContractServiceFacade(clientServiceFacade, vehicleServiceFacade, repairsCatalogServiceFacade,
                new ContractRepositoryInMemoryImpl(), new ContractNumberGenerator(), new ContractMapper());
    }

}
