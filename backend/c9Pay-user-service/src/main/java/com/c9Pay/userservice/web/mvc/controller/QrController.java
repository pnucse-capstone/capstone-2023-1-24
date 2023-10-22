package com.c9Pay.userservice.web.mvc.controller;


import com.c9Pay.userservice.config.Resilience4JConfig;
import com.c9Pay.userservice.data.dto.auth.ExchangeToken;
import com.c9Pay.userservice.data.entity.User;
import com.c9Pay.userservice.interceptor.GatewayValidation;
import com.c9Pay.userservice.security.jwt.JwtParser;
import com.c9Pay.userservice.security.jwt.JwtTokenUtil;
import com.c9Pay.userservice.web.client.AuthClient;
import com.c9Pay.userservice.web.docs.QrControllerDocs;
import com.c9Pay.userservice.web.exception.exceptions.InternalServerException;
import com.c9Pay.userservice.web.mvc.service.UserService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.c9Pay.userservice.config.Resilience4JConfig.circuitBreakerThrowable;
import static com.c9Pay.userservice.constant.CookieConstant.AUTHORIZATION_HEADER;
import static com.c9Pay.userservice.constant.ServiceConstant.API;
import static com.c9Pay.userservice.constant.ServiceConstant.AUTH_SERVICE;

/**
 * QR 정보 처리 관리하는 컨트롤러
 *
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class QrController implements QrControllerDocs {

    private final AuthClient authClient;

    private final JwtParser jwtParser;

    private final CircuitBreakerFactory circuitBreakerFactory;

    /**
     * 인증 토큰을 사용하여 QR 코드를 생성한다.
     *
     * @param token 사용자의 인증 토큰이 포함된 쿠키 값
     * @return QR 코드 생성 요청 결과를 포함하는 ResponseEntity 반환
     */
    @GetMapping(value = "/api/qr",produces = MediaType.APPLICATION_JSON_VALUE)
    @GatewayValidation(API)
    @Override
    public ResponseEntity<?> createQr(@CookieValue(AUTHORIZATION_HEADER) String token){
        CircuitBreaker circuitbreaker = circuitBreakerFactory.create("circuitbreaker");
        String serialNumber = jwtParser.getSerialNumberByToken(token);

        return circuitbreaker.run(() -> authClient.createQR(serialNumber),
                throwable -> circuitBreakerThrowable(AUTH_SERVICE));
    }
}
