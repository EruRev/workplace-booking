package com.irlix.traineeship.workplacebooking.exceptions;

public class PasswordChangeRequiredException extends RuntimeException {
    public PasswordChangeRequiredException(String message) {
        super(message);
    }
}