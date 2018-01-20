package pl.beck.vehicleworkshop.workersregistry.domain;

class Configuration {

    WorkersRegistryServiceFacade workersRegistryServiceFacade() {
        return new WorkersRegistryServiceFacade(new WorkerRepositoryInMemoryImpl(), new WorkerMapper());
    }

    WorkersRegistryServiceFacade workersRegistryServiceFacade(WorkerRepository workerRepository) {
        return new WorkersRegistryServiceFacade(workerRepository, new WorkerMapper());
    }
}
