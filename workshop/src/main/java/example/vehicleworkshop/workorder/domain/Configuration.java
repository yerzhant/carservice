package example.vehicleworkshop.workorder.domain;

import example.events.domain.EventBus;
import example.vehicleworkshop.client.domain.ClientServiceFacade;
import example.vehicleworkshop.contracts.domain.ContractServiceFacade;
import example.vehicleworkshop.repairscatalog.domain.RepairsCatalogServiceFacade;
import example.vehicleworkshop.vehiclecatalog.domain.VehicleServiceFacade;
import example.vehicleworkshop.workersregistry.domain.WorkersRegistryServiceFacade;
import example.vehicleworkshop.workorder.domain.event.WorkOrderEventsPublisher;

class Configuration {

    WorkOrderServiceFacade workOrderServiceFacade(ClientServiceFacade clientServiceFacade,
                                                  VehicleServiceFacade vehicleServiceFacade,
                                                  WorkersRegistryServiceFacade workersRegistryServiceFacade,
                                                  RepairsCatalogServiceFacade repairsCatalogServiceFacade,
                                                  ContractServiceFacade contractServiceFacade) {
        return new WorkOrderServiceFacade(clientServiceFacade, vehicleServiceFacade, workersRegistryServiceFacade,
                repairsCatalogServiceFacade, contractServiceFacade,
                new WorkOrderEventsPublisher(EventBus.singletonDefaultBus()),
                new WorkOrderRepositoryInMemoryImpl(), new WorkOderMapper()
        );
    }

    WorkOrderServiceFacade workOrderServiceFacade(ClientServiceFacade clientServiceFacade,
                                                  VehicleServiceFacade vehicleServiceFacade,
                                                  WorkersRegistryServiceFacade workersRegistryServiceFacade,
                                                  RepairsCatalogServiceFacade repairsCatalogServiceFacade,
                                                  WorkOrderEventsPublisher workOrderEventsPublisher,
                                                  ContractServiceFacade contractServiceFacade) {
        return new WorkOrderServiceFacade(clientServiceFacade, vehicleServiceFacade, workersRegistryServiceFacade,
                repairsCatalogServiceFacade, contractServiceFacade, workOrderEventsPublisher,
                new WorkOrderRepositoryInMemoryImpl(), new WorkOderMapper()
        );
    }

    WorkOrderServiceFacade workOrderServiceFacade(ClientServiceFacade clientServiceFacade,
                                                  VehicleServiceFacade vehicleServiceFacade,
                                                  WorkersRegistryServiceFacade workersRegistryServiceFacade,
                                                  RepairsCatalogServiceFacade repairsCatalogServiceFacade,
                                                  ContractServiceFacade contractServiceFacade,
                                                  WorkOrderEventsPublisher workOrderEventsPublisher,
                                                  WorkOrderRepository workOrderRepository) {
        return new WorkOrderServiceFacade(clientServiceFacade, vehicleServiceFacade, workersRegistryServiceFacade,
                repairsCatalogServiceFacade, contractServiceFacade, workOrderEventsPublisher, workOrderRepository, new WorkOderMapper());
    }
}
