package com.malik.restapi.controller;

import com.malik.restapi.exception.NonUniqueException;
import com.malik.restapi.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String handleNotFoundException(NotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(NonUniqueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleNonUniqueException(NonUniqueException e) {
        return e.getMessage();
    }
}