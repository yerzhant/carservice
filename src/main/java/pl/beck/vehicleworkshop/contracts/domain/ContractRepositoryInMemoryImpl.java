package pl.beck.vehicleworkshop.contracts.domain;

import org.springframework.util.ReflectionUtils;
import pl.beck.vehicleworkshop.contracts.domain.exceptions.ContractNotFoundException;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

class ContractRepositoryInMemoryImpl implements ContractRepository {

    private final Map<Long, Contract> contracts = new ConcurrentHashMap<>();

    private final AtomicLong atomicLong = new AtomicLong(1);

    @Override
    public void save(final Contract contract) {
        Objects.requireNonNull(contract);
        final long id = atomicLong.getAndIncrement();
        final Field id1 = ReflectionUtils.findField(contract.getClass(), "id");
        ReflectionUtils.makeAccessible(id1);
        ReflectionUtils.setField(id1, contract, id);
        contracts.put(id, contract);
    }

    @Override
    public Optional findOne(final Long contractId) {
        return Optional.ofNullable(contracts.get(contractId));
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
    public void delete(final Long id) {
        contracts.remove(id);
    }
}
