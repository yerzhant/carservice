package pl.beck.vehicleworkshop.repairscatalog.domain.exceptions;

import pl.beck.vehicleworkshop.sharedkernel.DomainException;

public class RepairCatalogNotFoundException extends DomainException {

    public RepairCatalogNotFoundException() {
        super("Repair service not found in catalog!");
    }
}
