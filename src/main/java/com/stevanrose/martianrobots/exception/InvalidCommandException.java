package com.stevanrose.martianrobots.exception;

public class InvalidCommandException extends RuntimeException {

    public InvalidCommandException(String message) {
        super(message);
    }
}
