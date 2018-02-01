package example.vehicleworkshop.contracts.domain;

import example.ddd.domain.BaseAggregateRoot;
import example.vehicleworkshop.contracts.domain.exceptions.ContractNotFoundException;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.stream.Collectors.toList;

class ContractRepositoryInMemoryImpl implements ContractRepository {

    private Map<BaseAggregateRoot.AggregateId, Contract> contracts = new ConcurrentHashMap<>();

    @Override
    public void save(Contract contract) {
        Objects.requireNonNull(contract);
        contracts.put(contract.getAggregateId(), contract);
    }

    @Override
    public void update(final Contract contract) {
        Objects.requireNonNull(contract);
        findOneOrThrow(contract.getAggregateId());
        save(contract);
    }

    @Override
    public Optional<Contract> findOne(final Contract.AggregateId id) {
        return contracts.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .filter(c -> c.getAggregateId().equals(id))
                .findFirst();
    }

    @Override
    public void delete(final Contract contract) {

    }

    @Override
    public Contract findByContractNumberOrThrow(String contractNumber) {
        return contracts.entrySet().stream()
                .map(Map.Entry::getValue)
                .filter(c -> c.getContractNumber().getNumber().equals(contractNumber))
                .findFirst()
                .orElseThrow(ContractNotFoundException::new);

    }

    @Override
    public Optional<Contract> findByVinForDates(String vin, LocalDate from, LocalDate to) {
        return contracts.entrySet().stream()
                .map(Map.Entry::getValue)
                .filter(e -> e.getVin().getVin().equals(vin))
                .filter(e -> e.getContractPeriod().overlap(ContractPeriod.of(from, to)))
                .findFirst();
    }

    @Override
    public Optional<Contract> findByPersonalNumberAndVinForDate(String personalNumber, String vin, LocalDate from, LocalDate to) {
        return contracts.entrySet().stream()
                .map(Map.Entry::getValue)
                .filter(e -> e.getClientData().getPersonalNumber().equals(personalNumber))
                .filter(e -> e.getVin().getVin().equals(vin))
                .filter(e -> e.getContractPeriod().overlap(ContractPeriod.of(from, to)))
                .findFirst();
    }

    @Override
    public Contract findByPersonalNumberAndVinForDateOrThrow(String personalNumber, String vin, LocalDate from, LocalDate to) {
        return findByPersonalNumberAndVinForDate(personalNumber, vin, from, to)
                .orElseThrow(ContractNotFoundException::new);
    }
}
