package hr.asc.appic.exception;

public class NoSuchEntityException extends RuntimeException {
	private static final long serialVersionUID = 3901320966220760218L;

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
