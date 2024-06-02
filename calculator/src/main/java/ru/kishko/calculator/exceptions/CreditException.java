package ru.kishko.calculator.exceptions;

public class CreditException extends RuntimeException {

    public CreditException() {
        super();
    }

    public CreditException(String message) {
        super(message);
    }

    public CreditException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreditException(Throwable cause) {
        super(cause);
    }

    protected CreditException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}