package example.ddd.infrastructure;

public class RepositoryException extends RuntimeException {

    public RepositoryException(final String message) {
        super(message);
    }

    public RepositoryException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
