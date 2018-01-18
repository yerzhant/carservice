package pl.beck.vehicleworkshop.repairscatalog.domain;

class Configuration {

    RepairsCatalogServiceFacade repairsCatalogServiceFacade() {
        return new RepairsCatalogServiceFacade(new RepairsCatalogInMemoryImpl());
    }
}
