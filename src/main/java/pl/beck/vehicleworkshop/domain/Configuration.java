package pl.beck.vehicleworkshop.domain;

class Configuration {



    VehicleWorkShopFacade vehicleWorkShopFacade(final ContractRepository contractRepository,
                                                final ClientRepository clientRepository,
                                                final VehicleRepository vehicleRepository) {

        ContractFactory contractFactory = new ContractFactory();
        return new VehicleWorkShopFacade(contractRepository, clientRepository, vehicleRepository, contractFactory);
    }

    VehicleWorkShopFacade vehicleWorkShopFacade() {
        ContractRepository contractRepository = new ContractRepositoryInMemoryImpl();
        ClientRepository clientRepository = new ClientRepositoryInMemoryImpl();
        VehicleRepository vehicleRepository = new VehicleRepositoryInMemoryImpl();

        ContractFactory contractFactory = new ContractFactory();

        return new VehicleWorkShopFacade(contractRepository, clientRepository, vehicleRepository, contractFactory);
    }
}
