package example.vehicleworkshop.contracts.domain;

import org.springframework.util.ReflectionUtils;
import example.vehicleworkshop.contracts.domain.exceptions.ContractNotFoundException;

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

    private Map<Long, Contract> contracts = new ConcurrentHashMap<>();

    private AtomicLong atomicLong = new AtomicLong(1);

    @Override
    public void save(Contract contract) {
        Objects.requireNonNull(contract);
        if(contract.getId() == null) {
            long id = atomicLong.getAndIncrement();
            Field id1 = ReflectionUtils.findField(contract.getClass(), "id");
            ReflectionUtils.makeAccessible(id1);
            ReflectionUtils.setField(id1, contract, id);
            contracts.put(contract.getId(), contract);
        } else {
            contracts.put(contract.getId(), contract);
        }

    }

    @Override
    public Optional findOne(Long contractId) {
        return Optional.ofNullable(contracts.get(contractId));
    }

    @Override
    public List<Contract> findAll(final String personalNumber) {
        return contracts.entrySet().stream()
                .map(Map.Entry::getValue)
                .filter(c -> c.getClientData().getPersonalNumber().equals(personalNumber))
                .collect(toList());
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
    public Contract findByVinForDatesOrThrow(String vin, LocalDate from, LocalDate to) {
        return findByVinForDates(vin, from, to)
                .orElseThrow(ContractNotFoundException::new);
    }

    @Override
    public Contract findByPersonalNumberAndVinForDateOrThrow(String personalNumber, String vin, LocalDate from, LocalDate to) {
        return findByPersonalNumberAndVinForDate(personalNumber, vin, from, to)
                .orElseThrow(ContractNotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        contracts.remove(id);
    }
}
