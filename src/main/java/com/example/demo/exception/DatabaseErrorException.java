package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLException;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "database connection error")
public class DatabaseErrorException extends SQLException {

}
