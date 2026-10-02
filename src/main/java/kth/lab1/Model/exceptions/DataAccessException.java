package kth.lab1.Model.exceptions;
/**
 * Thrown when a call to the database fails.
 */
public class DataAccessException extends Exception {

    /**
     * Create a new instance thrown because of the specified reason.
     *
     * @param reason Why the exception was thrown.
     */
    public DataAccessException(String reason) {
        super(reason);
    }

    /**
     * Create a new instance thrown because of the specified reason and exception.
     *
     * @param reason    Why the exception was thrown.
     * @param rootCause The exception that caused this exception to be thrown.
     */
    public DataAccessException(String reason, Throwable rootCause) {
        super(reason, rootCause);
    }
}
