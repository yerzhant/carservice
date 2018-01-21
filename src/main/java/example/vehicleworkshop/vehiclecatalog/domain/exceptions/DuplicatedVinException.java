package example.vehicleworkshop.vehiclecatalog.domain.exceptions;

import example.vehicleworkshop.sharedkernel.DomainException;

public class DuplicatedVinException extends DomainException {

    public DuplicatedVinException() {
        super("Duplicated vin number!");
    }
}
