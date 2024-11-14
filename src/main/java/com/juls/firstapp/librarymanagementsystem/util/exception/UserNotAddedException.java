package com.juls.firstapp.librarymanagementsystem.util.exception;

public class UserNotAddedException extends RuntimeException {

    public UserNotAddedException() {
        super();
    }

    public UserNotAddedException(String message) {
        super(message);
    }

    public UserNotAddedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotAddedException(Throwable cause) {
        super(cause);
    }

    protected UserNotAddedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
