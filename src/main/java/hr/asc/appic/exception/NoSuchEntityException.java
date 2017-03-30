package hr.asc.appic.exception;

public class NoSuchEntityException extends RuntimeException {

    public NoSuchEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchEntityException(String message) {
        super(message);
    }

    public NoSuchEntityException(Throwable cause) {
        super(cause);
    }
}
