package com.emmthias.cache.exception;

public class AlreadyKeyFoundException extends RuntimeException{
    public AlreadyKeyFoundException(String message) {
        super(message);
    }
}
