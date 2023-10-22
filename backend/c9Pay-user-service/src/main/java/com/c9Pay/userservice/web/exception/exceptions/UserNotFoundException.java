package com.c9Pay.userservice.web.exception.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * 사용자를 찾을 수 없는 예외를 나타내는 예외 클래스
 */
@ResponseStatus(BAD_REQUEST)
public class UserNotFoundException extends RuntimeException {
    /**
     * 주어진 문자열 포멧을 사용하여 예외 객체를 생성한다.
     *
     */
    public UserNotFoundException() {
        super("102");
    }
}
