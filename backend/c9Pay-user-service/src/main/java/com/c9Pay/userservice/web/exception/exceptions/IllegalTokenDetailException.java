package com.c9Pay.userservice.web.exception.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * 토큰 정보가 잘못된 경우를 나타내는 예외 클래스
 */
@ResponseStatus(BAD_REQUEST)
public class IllegalTokenDetailException extends RuntimeException {

    /**
     * 잘못된 토큰 정보로 예외 객체를 생성한다.
     */
    public IllegalTokenDetailException() {
        super("101");
    }
}
