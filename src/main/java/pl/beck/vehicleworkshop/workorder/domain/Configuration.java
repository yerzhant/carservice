package pl.beck.vehicleworkshop.workorder.domain;

import pl.beck.vehicleworkshop.client.domain.ClientServiceFacade;
import pl.beck.vehicleworkshop.contracts.domain.ContractServiceFacade;
import pl.beck.vehicleworkshop.repairscatalog.domain.RepairsCatalogServiceFacade;
import pl.beck.vehicleworkshop.vehiclecatalog.domain.VehicleServiceFacade;
import pl.beck.vehicleworkshop.workersregistry.domain.WorkersRegistryServiceFacade;

class Configuration {

    WorkOrderServiceFacade workOrderServiceFacade(ClientServiceFacade clientServiceFacade,
                                                  VehicleServiceFacade vehicleServiceFacade,
                                                  WorkersRegistryServiceFacade workersRegistryServiceFacade,
                                                  RepairsCatalogServiceFacade repairsCatalogServiceFacade,
                                                  ContractServiceFacade contractServiceFacade) {
        return new WorkOrderServiceFacade(clientServiceFacade, vehicleServiceFacade, workersRegistryServiceFacade,
                repairsCatalogServiceFacade, contractServiceFacade, new WorkOrderRepositoryInMemoryImpl(), new WorkOderMapper());
    }

    WorkOrderServiceFacade workOrderServiceFacade(ClientServiceFacade clientServiceFacade,
                                                  VehicleServiceFacade vehicleServiceFacade,
                                                  WorkersRegistryServiceFacade workersRegistryServiceFacade,
                                                  RepairsCatalogServiceFacade repairsCatalogServiceFacade,
                                                  ContractServiceFacade contractServiceFacade,
                                                  WorkOrderRepository workOrderRepository) {
        return new WorkOrderServiceFacade(clientServiceFacade, vehicleServiceFacade, workersRegistryServiceFacade,
                repairsCatalogServiceFacade, contractServiceFacade, workOrderRepository, new WorkOderMapper());
    }
}
