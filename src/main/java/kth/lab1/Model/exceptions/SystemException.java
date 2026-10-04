package kth.lab1.Model.exceptions;

public class SystemException extends RuntimeException {
    
    /**
     * Create a new instance thrown because of the specified reason.
     *
     * @param reason Why the exception was thrown.
     */
    public SystemException(String reason) {
        super(reason);
    }

    /**
     * Create a new instance thrown because of the specified reason and exception.
     *
     * @param reason    Why the exception was thrown.
     * @param rootCause The exception that caused this exception to be thrown.
     */
    public SystemException(String reason, Throwable rootCause) {
        super(reason, rootCause);
    }
}
