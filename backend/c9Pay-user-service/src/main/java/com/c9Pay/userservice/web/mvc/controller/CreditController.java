package com.c9Pay.userservice.web.mvc.controller;

import com.c9Pay.userservice.config.Resilience4JConfig;
import com.c9Pay.userservice.data.dto.credit.AccountDetails;
import com.c9Pay.userservice.data.entity.User;
import com.c9Pay.userservice.interceptor.GatewayValidation;
import com.c9Pay.userservice.security.jwt.JwtParser;
import com.c9Pay.userservice.web.client.CreditClient;
import com.c9Pay.userservice.security.jwt.JwtTokenUtil;
import com.c9Pay.userservice.data.dto.credit.ChargeForm;
import com.c9Pay.userservice.web.docs.CreditControllerDocs;
import com.c9Pay.userservice.web.exception.exceptions.IllegalTokenDetailException;
import com.c9Pay.userservice.web.exception.exceptions.InternalServerException;
import com.c9Pay.userservice.web.mvc.service.UserService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static com.c9Pay.userservice.config.Resilience4JConfig.circuitBreakerThrowable;
import static com.c9Pay.userservice.constant.CookieConstant.AUTHORIZATION_HEADER;
import static com.c9Pay.userservice.constant.ServiceConstant.API;
import static com.c9Pay.userservice.constant.ServiceConstant.CREDIT_SERVICE;


/**
 * 크레딧 서비스와 통신을 관리하는 컨트롤러
 *
 */
@Slf4j
@RestController
@RequestMapping("/api/credit")
@RequiredArgsConstructor
public class CreditController implements CreditControllerDocs {
    private final CreditClient creditClient;

    private final JwtParser jwtParser;

    private final CircuitBreakerFactory circuitBreakerFactory;


    /**
     * 사용자 크레딧을 충전한다.
     *
     * @param charge 크레딧 충전 정보를 담고 있는 ChargeForm 객체
     * @param token 사용자의 인증 토큰이 포함된 쿠키 값
     * @return 크레딧 충전이 성공한 경우 OK 응답을 반환
     */
    @Override
    @GatewayValidation(API)
    @PostMapping
    public ResponseEntity<?> chargeCredit(@Valid @RequestBody ChargeForm charge,
                                          @CookieValue(AUTHORIZATION_HEADER) String token){
        CircuitBreaker circuitbreaker = circuitBreakerFactory.create("circuitbreaker");
        String serialNumber = jwtParser.getSerialNumberByToken(token);

        circuitbreaker.run(() -> creditClient.loadCredit(serialNumber, new ChargeForm(charge.getQuantity())),
                throwable -> circuitBreakerThrowable(CREDIT_SERVICE));
        return ResponseEntity.ok().build();
    }

    /**
     * 사용자 크레딧 정보를 조회한다.
     *
     * @param token 사용자의 인증 토큰이 포함된 쿠키 값
     * @return 사용자의 현재 크레딧 정보를 담은 ResponseEntity 반환
     */
    @Override
    @GatewayValidation(API)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<?> getCredit(@CookieValue(AUTHORIZATION_HEADER) String token){
        CircuitBreaker circuitbreaker = circuitBreakerFactory.create("circuitbreaker");
        AccountDetails account = circuitbreaker.run(() -> creditClient.getAccount(jwtParser.getSerialNumberByToken(token)),
                throwable -> circuitBreakerThrowable(CREDIT_SERVICE)).getBody();

        ChargeForm form = new ChargeForm(Objects.requireNonNull(account).getCredit());
        return ResponseEntity.ok(form);
    }

}
