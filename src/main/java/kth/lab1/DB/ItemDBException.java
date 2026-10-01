package kth.lab1.DB;
/**
 * Thrown when a call to the bank database fails.
 */
public class ItemDBException extends Exception {

    /**
     * Create a new instance thrown because of the specified reason.
     *
     * @param reason Why the exception was thrown.
     */
    public ItemDBException(String reason) {
        super(reason);
    }

    /**
     * Create a new instance thrown because of the specified reason and exception.
     *
     * @param reason    Why the exception was thrown.
     * @param rootCause The exception that caused this exception to be thrown.
     */
    public ItemDBException(String reason, Throwable rootCause) {
        super(reason, rootCause);
    }
}
