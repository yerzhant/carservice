package example.vehicleworkshop.publishedlanguage;

import lombok.Value;

import javax.persistence.Embeddable;

@Embeddable
@Value
public class VehicleIdentificationNumber {

    private final String vin;

    public VehicleIdentificationNumber(final String vin) {
        this.vin = vin;
        validate();
    }

    private void validate() {
        //TODO add logic
    }

    public String getValue() {
        return vin;
    }
}
