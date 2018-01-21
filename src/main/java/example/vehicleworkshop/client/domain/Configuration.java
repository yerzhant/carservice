package example.vehicleworkshop.client.domain;

class Configuration {

    ClientServiceFacade clientServiceFacade() {
        return new ClientServiceFacade(new ClientRepositoryInMemoryImpl());
    }

    ClientServiceFacade clientServiceFacade(ClientRepository clientRepository) {
        return new ClientServiceFacade(clientRepository);
    }
}
