package example.vehicleworkshop.repairscatalog.domain;

import example.vehicleworkshop.repairscatalog.domain.exceptions.RepairCatalogNotFoundException;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

class RepairsCatalogInMemoryImpl implements RepairsCatalogRepository {


    private final Map<Long, RepairsCatalog> catalog = new ConcurrentHashMap<>();

    private final AtomicLong atomicLong = new AtomicLong(1);


    @Override
    public void save(RepairsCatalog repairsCatalog) {
        Objects.requireNonNull(repairsCatalog);
        if (repairsCatalog.getId() == null) {
            final long id = atomicLong.getAndIncrement();
            final Field id1 = ReflectionUtils.findField(repairsCatalog.getClass(), "id");
            ReflectionUtils.makeAccessible(id1);
            ReflectionUtils.setField(id1, repairsCatalog, id);
            catalog.put(repairsCatalog.getId(), repairsCatalog);
        } else {
            catalog.put(repairsCatalog.getId(), repairsCatalog);
        }
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
