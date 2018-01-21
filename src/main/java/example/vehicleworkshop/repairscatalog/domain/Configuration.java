package example.vehicleworkshop.repairscatalog.domain;

class Configuration {

    RepairsCatalogServiceFacade repairsCatalogServiceFacade() {
        return new RepairsCatalogServiceFacade(new RepairsCatalogInMemoryImpl());
    }
}
