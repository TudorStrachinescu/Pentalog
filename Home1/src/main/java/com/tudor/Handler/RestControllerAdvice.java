package com.tudor.Handler;

import com.tudor.exception.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RestControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApplicationException> handleAllExceptions(HttpServletRequest request, Exception ex) {
        return new ResponseEntity<>(new ApplicationException(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
