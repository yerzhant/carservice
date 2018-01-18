package pl.beck.vehicleworkshop.vehiclecatalog.domain.commandmodel;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddNewVehicleRequestDto {

    private String vin;

    private String brand;

    private String model;

    private double engineCapacity;

    private String engineCapacityUnitName;

    private String engineType;
}
