package com.example.demo.advice;
import com.example.demo.exception.*;
import com.example.demo.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalControllerException {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Response<String>> handleBadRequestException(BadRequestException ex) {
        Response<String> response = new Response<>();
        response.setSuccess(false);
        response.setResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DatabaseErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<Response<String>> handleDatabaseErrorException(DatabaseErrorException ex) {
        Response<String> response = new Response<>();
        response.setSuccess(false);
        response.setResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RegisterArgumentException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ResponseEntity<Response<String>> handleRegisterArgumentException(RegisterArgumentException ex) {
        Response<String> response = new Response<>();
        response.setSuccess(false);
        response.setResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ResponseEntity<Response<String>> handleAuthenticationException(AuthenticationException ex) {
        Response<String> response = new Response<>();
        response.setSuccess(false);
        response.setResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NoAccountFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<Response<String>> handleNotFoundException(NoAccountFoundException ex) {
        Response<String> response = new Response<>();
        response.setSuccess(false);
        response.setResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}


