package com.c9Pay.creditservice.web.exception.handler;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> duplicateUserExceptionHandler(Exception e, WebRequest request){
        ExceptionResponse exceptionResponse;
        if(e instanceof MethodArgumentNotValidException ex){
            exceptionResponse =
                    new ExceptionResponse(new Date(), e.getMessage().substring(0,34), request.getDescription(false));
            if(ex.hasFieldErrors()){
                for (FieldError fieldError: ex.getFieldErrors()){
                    exceptionResponse.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
                }
            }
            return ResponseEntity.status(BAD_REQUEST).body(exceptionResponse);
        }
        exceptionResponse =
                new ExceptionResponse(new Date(), e.getMessage(), request.getDescription(false));
        return ResponseEntity.status(BAD_REQUEST).body(exceptionResponse);
    }
}
