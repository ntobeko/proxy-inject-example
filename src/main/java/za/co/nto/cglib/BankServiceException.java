package za.co.nto.cglib;

/**
 * @author ntobeko.
 */
public class BankServiceException extends Exception {

    public BankServiceException() {
        super();
    }

    public BankServiceException(String message) {
        super(message);
    }

    public BankServiceException(Throwable cause) {
        super(cause);
    }

    public BankServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
