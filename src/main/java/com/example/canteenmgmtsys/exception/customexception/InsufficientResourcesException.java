package com.example.canteenmgmtsys.exception.customexception;

public class InsufficientResourcesException extends RuntimeException{
    public InsufficientResourcesException(String message) {
        super(message);
    }
}
