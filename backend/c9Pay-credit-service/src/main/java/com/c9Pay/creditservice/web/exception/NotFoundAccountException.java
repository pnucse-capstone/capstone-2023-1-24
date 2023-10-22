package com.c9Pay.creditservice.web.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class NotFoundAccountException extends RuntimeException{
    public NotFoundAccountException(String message){super(message);}
}
