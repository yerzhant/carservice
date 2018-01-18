package pl.beck.vehicleworkshop.vehiclecatalog.domain.exceptions;

import pl.beck.vehicleworkshop.sharedkernel.DomainException;

public class DuplicatedVinException extends DomainException {

    public DuplicatedVinException() {
        super("Duplicated vin number!");
    }
}
