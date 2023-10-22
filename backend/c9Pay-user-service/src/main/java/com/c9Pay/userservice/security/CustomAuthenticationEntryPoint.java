package com.c9Pay.userservice.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
/**
 * 커스텀 인증 진입 지점 (AuthenticationEntryPoint) 변경
 * exception 관리를 Controller 계층에 이임
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final HandlerExceptionResolver exceptionResolver;

    /**
     * 커스텀 인증 진입 지점을 생성한다.
     *
     * @param exceptionResolver 예외 처리를 위한 핸들러 예외 리졸버
     */
    public CustomAuthenticationEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {
        this.exceptionResolver = exceptionResolver;
    }

    /**
     * 인증 예외가 발생한 경우 예외 처리를 그대로 넘긴다.
     *
     * @param request        HTTP 요청
     * @param response       HTTP 응답
     * @param authException 인증 예외
     * @throws IOException      I/O 예외
     * @throws ServletException 서블릿 예외
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        exceptionResolver.resolveException(request, response, null, authException);
    }
}
