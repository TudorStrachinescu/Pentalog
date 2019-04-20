package com.tudor.exceptions;

/**
 * Custom exception for issues while loading account info from within file.
 */

public class LoadAccountException extends Exception {
    public LoadAccountException(String message) {
        super(message);
    }
}
