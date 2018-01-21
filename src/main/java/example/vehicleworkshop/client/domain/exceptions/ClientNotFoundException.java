package example.vehicleworkshop.client.domain.exceptions;

import example.vehicleworkshop.sharedkernel.DomainException;

public class ClientNotFoundException extends DomainException {

    public ClientNotFoundException() {
        super("Client not found!");
    }
}
