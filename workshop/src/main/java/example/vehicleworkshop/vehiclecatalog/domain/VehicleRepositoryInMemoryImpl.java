package example.vehicleworkshop.vehiclecatalog.domain;

import org.springframework.util.ReflectionUtils;
import example.vehicleworkshop.vehiclecatalog.domain.exceptions.DuplicatedVinException;
import example.vehicleworkshop.vehiclecatalog.domain.exceptions.VehicleNotFoundException;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Objects.requireNonNull;

class VehicleRepositoryInMemoryImpl implements VehicleRepository {


    private final Map<Long, Vehicle> vehicles = new ConcurrentHashMap<>();

    private final AtomicLong atomicLong = new AtomicLong(1);

    @Override
    public void save(Vehicle vehicle) {
        requireNonNull(vehicle);

        if(vehicle.getId() == null) {
            findByVin(vehicle.getVin().getValue()).ifPresent(v -> {
                throw new DuplicatedVinException();
            });


            final long id = atomicLong.getAndIncrement();

            final Field id1 = ReflectionUtils.findField(vehicle.getClass(), "id");
            ReflectionUtils.makeAccessible(id1);
            ReflectionUtils.setField(id1, vehicle, id);
            vehicles.put(vehicle.getId(), vehicle);
        } else {
            vehicles.put(vehicle.getId(), vehicle);
        }

    }

    @Override
    public Optional<Vehicle> findOne(Long id) {
        return Optional.ofNullable(vehicles.get(id));
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
        vehicles.remove(vehicle.getId());
    }
}
