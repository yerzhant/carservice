package example.vehicleworkshop.contracts.domain;

import example.ddd.domain.DomainRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

interface ContractRepository extends DomainRepository<Contract, Contract.AggregateId> {

    Contract findByContractNumberOrThrow(String contractNumber);

    Optional<Contract> findByVinForDates(String vin, LocalDate from, LocalDate to);

    Optional<Contract> findByPersonalNumberAndVinForDate(String personalNumber, String vin, LocalDate from, LocalDate to);

    Contract findByPersonalNumberAndVinForDateOrThrow(String personalNumber, String vin, LocalDate from, LocalDate to);

}
