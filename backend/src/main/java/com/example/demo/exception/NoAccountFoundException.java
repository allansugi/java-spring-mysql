package com.example.demo.exception;

public class NoAccountFoundException extends IllegalStateException {
    public NoAccountFoundException() {
    }

    public NoAccountFoundException(String s) {
        super(s);
    }

    public NoAccountFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoAccountFoundException(Throwable cause) {
        super(cause);
    }
}
