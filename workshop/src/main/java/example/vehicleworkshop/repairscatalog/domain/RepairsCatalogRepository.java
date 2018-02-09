package example.vehicleworkshop.repairscatalog.domain;


import example.ddd.domain.BaseAggregateRoot;
import example.ddd.domain.DomainRepository;

import java.util.Optional;

interface RepairsCatalogRepository extends DomainRepository<RepairsCatalog, BaseAggregateRoot.AggregateId> {

    Optional<RepairsCatalog> findByCatalogNumber(String catalogNumber);

    RepairsCatalog findByCatalogNumberOrThrow(String catalogNumber);
}
