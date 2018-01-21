package example.vehicleworkshop.vehiclecatalog.domain;


import lombok.Getter;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
class VehicleDetails {

    private String brand;

    private String model;

    private EngineType engineType;

    private EngineCapacity engineCapacity;

    enum EngineType {
        PETROL, DIESEL
    }

    public VehicleDetails(String brand, String model, String engineType, EngineCapacity engineCapacity) {
        this.brand = Objects.requireNonNull(brand);
        this.model = Objects.requireNonNull(model);
        this.engineCapacity = engineCapacity;
        this.engineType = EngineType.valueOf(engineType);
    }

    VehicleDetails(String brand, String model, EngineType engineType, EngineCapacity engineCapacity) {
        this.brand = Objects.requireNonNull(brand);
        this.model = Objects.requireNonNull(model);
        this.engineCapacity = engineCapacity;
        this.engineType = Objects.requireNonNull(engineType);
    }

    @Override
    public int hashCode() {
        int result = brand.hashCode();
        result = 31 * result + model.hashCode();
        result = 31 * result + engineType.hashCode();
        result = 31 * result + engineCapacity.hashCode();
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final VehicleDetails that = (VehicleDetails) o;

        if (!brand.equals(that.brand)) return false;
        if (!model.equals(that.model)) return false;
        if (engineType != that.engineType) return false;
        return engineCapacity.equals(that.engineCapacity);
    }
}
