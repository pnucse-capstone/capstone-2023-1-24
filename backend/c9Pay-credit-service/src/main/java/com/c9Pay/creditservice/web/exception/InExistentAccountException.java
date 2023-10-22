package com.c9Pay.creditservice.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class InExistentAccountException extends RuntimeException {
    public InExistentAccountException(){
        super("Account is Already exists");
    }
}
