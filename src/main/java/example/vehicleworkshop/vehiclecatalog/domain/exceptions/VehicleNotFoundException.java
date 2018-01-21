package example.vehicleworkshop.vehiclecatalog.domain.exceptions;

import example.vehicleworkshop.sharedkernel.DomainException;

public class VehicleNotFoundException extends DomainException {

    public VehicleNotFoundException() {
        super("Vehicle not found!");
    }
}
