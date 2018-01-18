package pl.beck.vehicleworkshop.vehiclecatalog.domain.exceptions;

import pl.beck.vehicleworkshop.sharedkernel.DomainException;

public class VehicleNotFoundException extends DomainException {

    public VehicleNotFoundException() {
        super("Vehicle not found!");
    }
}
