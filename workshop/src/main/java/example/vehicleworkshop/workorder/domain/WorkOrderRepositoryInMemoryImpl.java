package example.vehicleworkshop.workorder.domain;

import example.ddd.domain.BaseAggregateRoot;
import example.vehicleworkshop.workorder.domain.exceptions.WorkOrderNotFoundException;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

class WorkOrderRepositoryInMemoryImpl implements WorkOrderRepository {

    private Map<BaseAggregateRoot.AggregateId, WorkOder> orders = new ConcurrentHashMap<>();

    @Override
    public void save(final WorkOder workOder) {
        Objects.requireNonNull(workOder);
        orders.put(workOder.getAggregateId(), workOder);

    }

    @Override
    public void update(final WorkOder workOder) {
        Objects.requireNonNull(workOder);
        findOneOrThrow(workOder.getAggregateId());
        save(workOder);
    }

    @Override
    public Optional<WorkOder> findOne(final BaseAggregateRoot.AggregateId id) {
        return orders.entrySet().stream()
                .map(Map.Entry::getValue)
                .filter(v -> v.getAggregateId().equals(id))
                .findFirst();
    }

    @Override
    public void delete(final WorkOder workOder) {
        orders.remove(workOder.getAggregateId());
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
