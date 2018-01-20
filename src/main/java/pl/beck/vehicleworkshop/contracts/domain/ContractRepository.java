package pl.beck.vehicleworkshop.contracts.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

interface ContractRepository {

    void save(Contract contract);

    Optional<Contract> findOne(Long contractId);

    List<Contract> findAll(String personalNumber);

    Contract findByContractNumberOrThrow(String contractNumber);

    Optional<Contract> findByVinForDates(String vin, LocalDate from, LocalDate to);

    Optional<Contract> findByPersonalNumberAndVinForDate(String personalNumber, String vin, LocalDate from, LocalDate to);

    Contract findByVinForDatesOrThrow(String vin, LocalDate from, LocalDate to);

    Contract findByPersonalNumberAndVinForDateOrThrow(String personalNumber, String vin, LocalDate from, LocalDate to);

    void delete(Long id);
}
