package com.ms.user.controllers.exception;

public class EmailNotExistent extends RuntimeException {
    public EmailNotExistent(String message){
        super(message);
    }
}
