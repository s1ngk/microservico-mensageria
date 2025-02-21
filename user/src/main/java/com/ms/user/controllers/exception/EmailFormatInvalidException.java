package com.ms.user.controllers.exception;

public class EmailFormatInvalidException extends RuntimeException {
    public EmailFormatInvalidException(String message) {
        super(message);
    }
}
