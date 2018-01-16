package pl.beck.vehicleworkshop.domain;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

class VehicleRepositoryInMemoryImpl implements VehicleRepository {


    private final Map<String, Vehicle> vehicles = new ConcurrentHashMap<>();

    @Override
    public void save(final Vehicle vehicle) {
        requireNonNull(vehicle);
        vehicles.put(vehicle.getVinNumber(), vehicle);
    }

    @Override
    public Optional<Vehicle> findOne(final String vinNumber) {
        return Optional.ofNullable(vehicles.get(vinNumber));
    }

    @Override
    public void delete(final Vehicle vehicle) {
        vehicles.remove(vehicle);
    }
}
