package pl.beck.vehicleworkshop.client.domain.exceptions;

import pl.beck.vehicleworkshop.sharedkernel.DomainException;

public class DuplicatedPersonalNumberException extends DomainException {

    public DuplicatedPersonalNumberException() {
        super("Client with given personal number exists!");
    }
}
