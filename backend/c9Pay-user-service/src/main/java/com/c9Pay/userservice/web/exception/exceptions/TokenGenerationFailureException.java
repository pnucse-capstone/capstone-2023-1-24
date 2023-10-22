package com.c9Pay.userservice.web.exception.exceptions;

/**
 * 토큰 생성 실패를 나타내는 예외 클래스
 */
public class TokenGenerationFailureException extends RuntimeException {
    /**
     * 토큰 생성 실패 예외 객체를 생성한다.
     */
    public TokenGenerationFailureException(){
        super("500");
    }
}
