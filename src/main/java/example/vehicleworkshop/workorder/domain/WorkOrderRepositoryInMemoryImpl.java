package example.vehicleworkshop.workorder.domain;

import org.springframework.util.ReflectionUtils;
import example.vehicleworkshop.workorder.domain.exceptions.WorkOrderNotFoundException;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

class WorkOrderRepositoryInMemoryImpl implements WorkOrderRepository {

    private Map<Long, WorkOder> orders = new ConcurrentHashMap<>();

    private final AtomicLong atomicLong = new AtomicLong(1);

    @Override
    public void save(final WorkOder workOder) {
        Objects.requireNonNull(workOder);
        if(workOder.getId() == null) {
            final long id = atomicLong.getAndIncrement();
            final Field id1 = ReflectionUtils.findField(workOder.getClass(), "id");
            ReflectionUtils.makeAccessible(id1);
            ReflectionUtils.setField(id1, workOder, id);
            orders.put(id, workOder);
        } else {
            orders.put(workOder.getId(), workOder);
        }

    }

    @Override
    public Optional<WorkOder> findOne(final Long id) {
        return Optional.ofNullable(orders.get(id));
    }

    @Override
    public Optional<WorkOder> findOneByWorkOrderNumber(final String number) {
        return orders.entrySet().stream()
                .map(Map.Entry::getValue)
                .filter(o -> o.getWorkOrderNumber().getNumber().equals(number))
                .findFirst();
    }

    @Override
    public WorkOder findOneByWorkOrderNumberOrThrow(final String number) {
        return findOneByWorkOrderNumber(number).orElseThrow(WorkOrderNotFoundException::new);
    }
}
