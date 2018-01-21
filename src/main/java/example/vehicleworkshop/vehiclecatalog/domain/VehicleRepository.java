package example.vehicleworkshop.vehiclecatalog.domain;


import java.util.Optional;

interface VehicleRepository {

    void save(Vehicle vehicle);

    Optional<Vehicle> findOne(Long id);

    Optional<Vehicle> findByVin(String vin);

    Vehicle findByVinOrThrow(String vin);

    void delete(Vehicle client);
}
