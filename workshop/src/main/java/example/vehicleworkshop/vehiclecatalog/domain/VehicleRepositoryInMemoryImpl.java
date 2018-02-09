package example.vehicleworkshop.vehiclecatalog.domain;

import example.ddd.domain.BaseAggregateRoot;
import example.vehicleworkshop.vehiclecatalog.domain.exceptions.DuplicatedVinException;
import example.vehicleworkshop.vehiclecatalog.domain.exceptions.VehicleNotFoundException;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Objects.requireNonNull;

class VehicleRepositoryInMemoryImpl implements VehicleRepository {


    private final Map<BaseAggregateRoot.AggregateId, Vehicle> vehicles = new ConcurrentHashMap<>();

    @Override
    public void save(Vehicle vehicle) {
        requireNonNull(vehicle);
        vehicles.put(vehicle.getAggregateId(), vehicle);
    }

    @Override
    public void update(final Vehicle vehicle) {
        requireNonNull(vehicle);
        findOneOrThrow(vehicle.getAggregateId());
        save(vehicle);
    }

    @Override
    public Optional<Vehicle> findOne(final BaseAggregateRoot.AggregateId id) {
        return vehicles.entrySet().stream()
                .map(Map.Entry::getValue)
                .filter(v -> v.getAggregateId().equals(id))
                .findFirst();
    }


    @Override
    public Optional<Vehicle> findByVin(final String vin) {
        return vehicles.entrySet().stream()
                .map(Map.Entry::getValue)
                .filter(v -> v.getVin().getValue().equals(vin))
                .findFirst();
    }

    @Override
    public Vehicle findByVinOrThrow(final String vin) {
        return findByVin(vin).orElseThrow(VehicleNotFoundException::new);
    }

    @Override
    public void delete(Vehicle vehicle) {
        vehicles.remove(vehicle.getAggregateId());
    }
}
