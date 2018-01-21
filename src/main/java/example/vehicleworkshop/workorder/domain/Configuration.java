package example.vehicleworkshop.workorder.domain;

import example.vehicleworkshop.client.domain.ClientServiceFacade;
import example.vehicleworkshop.contracts.domain.ContractServiceFacade;
import example.vehicleworkshop.repairscatalog.domain.RepairsCatalogServiceFacade;
import example.vehicleworkshop.vehiclecatalog.domain.VehicleServiceFacade;
import example.vehicleworkshop.workersregistry.domain.WorkersRegistryServiceFacade;

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
