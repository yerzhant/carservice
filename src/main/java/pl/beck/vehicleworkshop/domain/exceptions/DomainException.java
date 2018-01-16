package pl.beck.vehicleworkshop.domain.exceptions;

import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {

    private int code;

    public DomainException() {
        super();
    }

    public DomainException(String message) {
        super(message);
    }

    public DomainException(int code, String message) {
        super(message);
        this.code = code;
    }

    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
