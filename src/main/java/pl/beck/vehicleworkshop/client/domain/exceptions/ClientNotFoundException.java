package pl.beck.vehicleworkshop.client.domain.exceptions;

import pl.beck.vehicleworkshop.sharedkernel.DomainException;

public class ClientNotFoundException extends DomainException {

    public ClientNotFoundException() {
        super("Client not found!");
    }
}
