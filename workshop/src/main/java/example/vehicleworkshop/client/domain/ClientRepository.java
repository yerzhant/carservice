package example.vehicleworkshop.client.domain;

import example.ddd.domain.BaseAggregateRoot;
import example.ddd.domain.DomainRepository;

import java.util.Optional;

interface ClientRepository extends DomainRepository<Client, BaseAggregateRoot.AggregateId> {

    Client findByPersonalNumberOrThrow(String personalNumber);

    Optional<Client> findByPersonalNumber(String personalNumber);
}
