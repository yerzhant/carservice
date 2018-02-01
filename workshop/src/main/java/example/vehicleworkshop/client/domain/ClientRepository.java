package example.vehicleworkshop.client.domain;

import example.ddd.domain.BaseAggregateRoot;
import example.ddd.infrastructure.DomainRepository;

import java.util.Optional;

interface ClientRepository extends DomainRepository<Client, BaseAggregateRoot.AggregateId> {

    void save(Client client);

    Client findByPersonalNumberOrThrow(String personalNumber);

    Optional<Client> findByPersonalNumber(String personalNumber);

    void delete(Client client);
}
