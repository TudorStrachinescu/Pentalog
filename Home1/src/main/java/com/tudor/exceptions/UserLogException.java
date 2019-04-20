package com.tudor.exceptions;

/**
 * Custom exception for issues with user authentication.
 */

public class UserLogException extends Exception{
    public UserLogException(String message) {
        super(message);
    }
}
