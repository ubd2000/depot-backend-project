package com.depot.shopping.error.exception;

public class CustomInvalidRefreshTokenException extends RuntimeException {
    public CustomInvalidRefreshTokenException(String message) {
        super(message);
    }
}