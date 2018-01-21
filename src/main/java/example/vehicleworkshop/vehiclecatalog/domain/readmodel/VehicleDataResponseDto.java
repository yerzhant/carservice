package example.vehicleworkshop.vehiclecatalog.domain.readmodel;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VehicleDataResponseDto {

    private Long id;

    private String vin;

    private String brand;

    private String model;

    private double engineCapacity;

    private String engineType;
}
