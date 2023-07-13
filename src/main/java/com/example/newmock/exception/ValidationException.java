package com.example.newmock.exception;

public class ValidationException extends RuntimeException{
    public ValidationException(String errorMessage){
        super(errorMessage);
    }
}
