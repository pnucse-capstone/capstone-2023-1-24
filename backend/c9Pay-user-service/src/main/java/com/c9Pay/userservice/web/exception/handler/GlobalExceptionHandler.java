package com.c9Pay.userservice.web.exception.handler;

import com.c9Pay.userservice.web.exception.exceptions.DuplicatedUserException;
import com.c9Pay.userservice.web.exception.exceptions.UserNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * 전역 예외 처리를 담담하는 클래스
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validationException(){
        ExceptionResponse response = new ExceptionResponse("104");
        return ResponseEntity.badRequest().body(response);
    }
    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<?> accessDeniedExceptionHandler(){
        ExceptionResponse response = new ExceptionResponse("101");
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * 모든 예외에 대한 처리를 수행한다.
     *
     * @param e 발생한 예외 객체
     * @return 예외 정보를 담는 ResponseEntity 객체
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> duplicateUserExceptionHandler(Exception e){
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage());
        return ResponseEntity.badRequest().body(exceptionResponse);
    }

}
