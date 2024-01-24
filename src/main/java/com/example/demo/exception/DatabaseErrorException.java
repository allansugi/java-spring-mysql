package com.example.demo.exception;

import java.sql.SQLException;

public class DatabaseErrorException extends SQLException {
    public DatabaseErrorException(String reason, String SQLState, int vendorCode) {
        super(reason, SQLState, vendorCode);
    }

    public DatabaseErrorException(String reason, String SQLState) {
        super(reason, SQLState);
    }

    public DatabaseErrorException(String reason) {
        super(reason);
    }

    public DatabaseErrorException() {
    }

    public DatabaseErrorException(Throwable cause) {
        super(cause);
    }

    public DatabaseErrorException(String reason, Throwable cause) {
        super(reason, cause);
    }

    public DatabaseErrorException(String reason, String sqlState, Throwable cause) {
        super(reason, sqlState, cause);
    }

    public DatabaseErrorException(String reason, String sqlState, int vendorCode, Throwable cause) {
        super(reason, sqlState, vendorCode, cause);
    }
}
