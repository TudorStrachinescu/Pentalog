package com.tudor.exceptions;

/**
 * Custom exception for issues with user authentication.
 */

public class UserException extends Exception{
    public UserException(String message) {
        super(message);
    }
}
