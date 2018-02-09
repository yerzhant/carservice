package example.vehicleworkshop.repairscatalog.domain;

import example.ddd.domain.BaseAggregateRoot;
import example.vehicleworkshop.repairscatalog.domain.exceptions.RepairCatalogNotFoundException;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

class RepairsCatalogInMemoryImpl implements RepairsCatalogRepository {


    private final Map<BaseAggregateRoot.AggregateId, RepairsCatalog> catalog = new ConcurrentHashMap<>();


    @Override
    public void save(RepairsCatalog repairsCatalog) {
        Objects.requireNonNull(repairsCatalog);
        catalog.put(repairsCatalog.getAggregateId(), repairsCatalog);
    }

    @Override
    public void update(final RepairsCatalog repairsCatalog) {
        Objects.requireNonNull(repairsCatalog);
        findOneOrThrow(repairsCatalog.getAggregateId());
        save(repairsCatalog);
    }

    @Override
    public Optional<RepairsCatalog> findOne(final BaseAggregateRoot.AggregateId id) {
        return catalog.entrySet().stream()
                .map(Map.Entry::getValue)
                .filter(v -> v.getAggregateId().equals(id))
                .findFirst();
    }

    @Override
    public void delete(final RepairsCatalog repairsCatalog) {
        Objects.requireNonNull(repairsCatalog);
        catalog.remove(repairsCatalog.getAggregateId());
    }

    @Override
    public Optional<RepairsCatalog> findByCatalogNumber(final String catalogNumber) {
        return catalog.entrySet().stream()
                .map(Map.Entry::getValue)
                .filter(r -> r.getNumber().getNumber().equals(catalogNumber))
                .findFirst();
    }

    @Override
    public RepairsCatalog findByCatalogNumberOrThrow(String catalogNumber) {
        return findByCatalogNumber(catalogNumber).orElseThrow(RepairCatalogNotFoundException::new);
    }


}
