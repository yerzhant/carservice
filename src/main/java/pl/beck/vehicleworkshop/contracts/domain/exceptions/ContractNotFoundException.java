package pl.beck.vehicleworkshop.contracts.domain.exceptions;

import pl.beck.vehicleworkshop.sharedkernel.DomainException;

public class ContractNotFoundException extends DomainException {

    public ContractNotFoundException() {
        super("Contract not found!");
    }
}
