package pl.beck.vehicleworkshop.repairscatalog.domain;


import java.util.Optional;

interface RepairsCatalogRepository {

    void save(RepairsCatalog repairsCatalog);

    Optional<RepairsCatalog> findByCatalogNumber(String catalogNumber);

    RepairsCatalog findByCatalogNumberOrThrow(String catalogNumber);
}
