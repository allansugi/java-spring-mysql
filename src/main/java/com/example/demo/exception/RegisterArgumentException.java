package com.example.demo.exception;

public class RegisterArgumentException extends IllegalArgumentException{
    public RegisterArgumentException() {
    }

    public RegisterArgumentException(String s) {
        super(s);
    }

    public RegisterArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegisterArgumentException(Throwable cause) {
        super(cause);
    }
}
