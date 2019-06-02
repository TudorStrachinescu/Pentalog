package com.tudor.exception;

public class ApplicationException extends Exception {
    public ApplicationException() {
        super("An error occurred. Please try again");
    }
}
