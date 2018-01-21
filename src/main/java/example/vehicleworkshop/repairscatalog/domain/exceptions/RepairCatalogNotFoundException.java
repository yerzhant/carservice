package example.vehicleworkshop.repairscatalog.domain.exceptions;

import example.vehicleworkshop.sharedkernel.DomainException;

public class RepairCatalogNotFoundException extends DomainException {

    public RepairCatalogNotFoundException() {
        super("Repair service not found in catalog!");
    }
}
