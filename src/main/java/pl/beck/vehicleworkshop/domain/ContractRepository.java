package pl.beck.vehicleworkshop.domain;

import java.util.Optional;

interface ContractRepository {

    void save(Contract contract);

    Optional<Contract> findOne(Long contractId);

    void delete(Long id);
}
