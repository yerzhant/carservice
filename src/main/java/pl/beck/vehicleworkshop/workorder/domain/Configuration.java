package pl.beck.vehicleworkshop.workorder.domain;

import pl.beck.vehicleworkshop.client.domain.ClientServiceFacade;
import pl.beck.vehicleworkshop.vehiclecatalog.domain.VehicleServiceFacade;
import pl.beck.vehicleworkshop.workersregistry.domain.WorkersRegistryServiceFacade;

class Configuration {

    WorkOrderServiceFacade workOrderServiceFacade(ClientServiceFacade clientServiceFacade,
                                                  VehicleServiceFacade vehicleServiceFacade,
                                                  WorkersRegistryServiceFacade workersRegistryServiceFacade
                                                  ) {
        return new WorkOrderServiceFacade(clientServiceFacade, vehicleServiceFacade, workersRegistryServiceFacade,
                new WorkOrderRepositoryInMemoryImpl(), new WorkOderMapper());
    }
}
