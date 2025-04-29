package com.depot.shopping.error.exception;

public class CustomUserConflictException extends RuntimeException {
    public CustomUserConflictException(String message) {
        super(message);
    }
}