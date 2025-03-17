package com.depot.shopping.error.exception;

public class CustomUserWrongPwdException extends RuntimeException {
    public CustomUserWrongPwdException(String message) {
        super(message);
    }
}