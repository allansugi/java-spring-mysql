package com.example.demo.advice;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.DatabaseErrorException;
import com.example.demo.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Response<String>> handleBadRequestException(BadRequestException ex) {
        Response<String> response = new Response<>();
        response.setSuccess(false);
        response.setResponse(ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(DatabaseErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Response<String>> handleDatabaseErrorException(DatabaseErrorException ex) {
        Response<String> response = new Response<>();
        response.setSuccess(false);
        response.setResponse(ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

}


