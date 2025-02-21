package com.ms.user.controllers.exception;

public class PasswordMatchesException extends RuntimeException {
    public PasswordMatchesException(String message) {
        super(message);
    }
}
