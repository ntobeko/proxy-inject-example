package za.co.nto.cglib;

/**
 * @author ntobeko.
 */
public class BankDataException extends Exception {

    public BankDataException() {
        super();
    }

    public BankDataException(String message) {
        super(message);
    }

    public BankDataException(Throwable cause) {
        super(cause);
    }

    public BankDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
