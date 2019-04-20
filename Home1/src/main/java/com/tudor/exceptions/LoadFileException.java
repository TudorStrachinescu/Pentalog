package com.tudor.exceptions;

/**
 * Custom exception for issues with loading the resource accounts file.
 */

public class LoadFileException extends Exception {
    public LoadFileException(String message) {
        super(message);
    }
}
