package exceptions;

public class BusinessLogicException extends Exception {
    private int code;

    public int getCode() {
        return code;
    }

    public BusinessLogicException(String message) {
        super(message, null, false, false);
    }
}
