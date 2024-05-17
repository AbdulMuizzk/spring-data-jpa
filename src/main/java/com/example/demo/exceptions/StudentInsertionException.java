package com.example.demo.exceptions;

public class StudentInsertionException extends RuntimeException{
    public StudentInsertionException(String message) {
        super(message);
    }
}
