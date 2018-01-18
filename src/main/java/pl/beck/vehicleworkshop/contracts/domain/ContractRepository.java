package pl.beck.vehicleworkshop.contracts.domain;

import java.util.Optional;

interface ContractRepository {

    void save(Contract contract);

    Optional<Contract> findOne(Long contractId);

    Contract findByContractNumberOrThrow(String contractNumber);

    void delete(Long id);
}
