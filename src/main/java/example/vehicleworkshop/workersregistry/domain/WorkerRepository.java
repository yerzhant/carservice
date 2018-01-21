package example.vehicleworkshop.workersregistry.domain;

import java.util.Optional;

interface WorkerRepository {


    void save(Worker worker);

    Optional<Worker> findOne(Long id);

    Worker findOneOrThrow(Long id);

    Worker findByPersonalNumberOrThrow(String personalNumber);

    Optional<Worker> findByPersonalNumber(String personalNumber);

    void delete(Worker client);
}
