package pl.beck.vehicleworkshop.workersregistry.domain;

import org.springframework.util.ReflectionUtils;
import pl.beck.vehicleworkshop.workersregistry.domain.exceptions.WorkerNotFoundException;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Objects.requireNonNull;

class WorkerRepositoryInMemoryImpl implements WorkerRepository {
    private final Map<Long, Worker> workers = new ConcurrentHashMap<>();

    private final AtomicLong atomicLong = new AtomicLong(1);

    @Override
    public void save(final Worker worker) {
        requireNonNull(worker);

        if(worker.getId() == null) {
            findByPersonalNumber(worker.getPersonalNumber()).ifPresent(c -> {
                throw new RuntimeException("Duplicated personalNumber");
            });

            final long id = atomicLong.getAndIncrement();
            final Field id1 = ReflectionUtils.findField(worker.getClass(), "id");
            ReflectionUtils.makeAccessible(id1);
            ReflectionUtils.setField(id1, worker, id);
            workers.put(worker.getId(), worker);
        } else {
            workers.put(worker.getId(), worker);
        }
    }

    @Override
    public Optional<Worker> findOne(final Long id) {
        return Optional.ofNullable(workers.get(id));
    }

    @Override
    public Worker findOneOrThrow(final Long id) {
        return Optional.ofNullable(workers.get(id)).orElseThrow(WorkerNotFoundException::new);
    }

    @Override
    public Worker findByPersonalNumberOrThrow(final String personalNumber) {
        return findByPersonalNumber(personalNumber).orElseThrow(WorkerNotFoundException::new);
    }

    @Override
    public Optional<Worker> findByPersonalNumber(final String personalNumber) {
        return workers.entrySet().stream()
                .filter(e -> e.getValue().getPersonalNumber().equals(personalNumber))
                .map(Map.Entry::getValue)
                .findFirst();
    }

    @Override
    public void delete(final Worker worker) {
        workers.remove(worker.getId());
    }
}
