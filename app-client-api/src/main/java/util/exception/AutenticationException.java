package util.exception;

public class AutenticationException extends Exception {
    public AutenticationException(String message) {
        super(message);
    }

    public AutenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
