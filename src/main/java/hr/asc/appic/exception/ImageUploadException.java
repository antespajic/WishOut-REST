package hr.asc.appic.exception;

public class ImageUploadException extends RuntimeException {

    public ImageUploadException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImageUploadException(String message) {
        super(message);
    }

    public ImageUploadException(Throwable cause) {
        super(cause);
    }
}
