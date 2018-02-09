package example.vehicleworkshop.vehiclecatalog.domain;


import example.ddd.domain.BaseAggregateRoot;
import example.ddd.domain.DomainRepository;

import java.util.Optional;

interface VehicleRepository extends DomainRepository<Vehicle, BaseAggregateRoot.AggregateId> {

    Optional<Vehicle> findByVin(String vin);

    Vehicle findByVinOrThrow(String vin);
}
