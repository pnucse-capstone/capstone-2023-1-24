package com.c9Pay.creditservice.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class InsufficientAccountBalanceException extends RuntimeException{

    public InsufficientAccountBalanceException(){
        super("The account balance is insufficient");
    }

}
