package example.vehicleworkshop.contracts.domain.exceptions;

import example.vehicleworkshop.sharedkernel.DomainException;

public class ContractNotFoundException extends DomainException {

    public ContractNotFoundException() {
        super("Contract not found!");
    }
}
