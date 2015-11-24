package za.co.nto;

/**
 * @author ntobeko.
 */
public class TestException extends Exception {

    public TestException() {
        super();
    }

    public TestException(String message) {
        super(message);
    }

    public TestException(Throwable cause) {
        super(cause);
    }

    public TestException(String message, Throwable cause) {
        super(message, cause);
    }
}
