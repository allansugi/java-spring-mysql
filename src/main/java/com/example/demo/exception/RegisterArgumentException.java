package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

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
