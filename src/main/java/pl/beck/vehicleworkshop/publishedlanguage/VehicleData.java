package pl.beck.vehicleworkshop.publishedlanguage;

import lombok.Value;
import pl.beck.vehicleworkshop.publishedlanguage.VehicleIdentificationNumber;

import javax.persistence.Embeddable;

@Value
@Embeddable
public class VehicleData {

    private VehicleIdentificationNumber vin;

    private String brand;

    private String model;

    private String engineType;
}
