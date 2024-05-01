package util.exception;

public class ExceptionMonitoring extends Exception {
    public ExceptionMonitoring(String message) {
        super(message);
    }

    public ExceptionMonitoring(String message, Throwable cause) {
        super(message, cause);
    }
}
