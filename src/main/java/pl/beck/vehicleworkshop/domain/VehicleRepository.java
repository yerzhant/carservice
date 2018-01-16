package pl.beck.vehicleworkshop.domain;

import java.util.Optional;

interface VehicleRepository {

    void save(Vehicle client);

    Optional<Vehicle> findOne(String  vinNumber);

    void delete(Vehicle client);
}
