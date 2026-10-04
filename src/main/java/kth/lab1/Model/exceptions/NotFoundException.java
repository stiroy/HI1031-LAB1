package kth.lab1.Model.exceptions;

/**
 * Thrown when a call to the database returns a empty set.
 */
public class NotFoundException extends RuntimeException {

    /**
     * Create a new instance thrown because of the specified reason.
     *
     * @param reason Why the exception was thrown.
     */
    public NotFoundException(String reason) {
        super(reason);
    }

    /**
     * Create a new instance thrown because of the specified reason and exception.
     *
     * @param reason    Why the exception was thrown.
     * @param rootCause The exception that caused this exception to be thrown.
     */
    public NotFoundException(String reason, Throwable rootCause) {
        super(reason, rootCause);
    }
}
