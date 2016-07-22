package ua.com.as.exception;

/**
 * <p>Thrown to indicate that there are some errors with scanning packages.
 */
public class ScanningException extends RuntimeException {

    /**
     * <p>Constructs a new scanning exception with the specified message.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ScanningException(String message) {
        super(message);
    }

    /**
     * <p>Constructs a new scanning exception with the specified detail message and
     * cause.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method). Null value permitted.
     */
    public ScanningException(String message, Throwable cause) {
        super(message, cause);
    }
}
