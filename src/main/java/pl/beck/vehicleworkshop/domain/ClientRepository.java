package pl.beck.vehicleworkshop.domain;

import java.util.Optional;

interface ClientRepository {

    void save(Client client);

    Optional<Client> findOne(Long id);

    Optional<Client> findByPersonalNumber(String personalNumber);

    void delete(Client client);
}
