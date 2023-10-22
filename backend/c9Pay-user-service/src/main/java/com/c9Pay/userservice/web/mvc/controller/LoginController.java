package com.c9Pay.userservice.web.mvc.controller;


import com.c9Pay.userservice.constant.BearerConstant;
import com.c9Pay.userservice.constant.CookieConstant;
import com.c9Pay.userservice.data.dto.user.LoginForm;
import com.c9Pay.userservice.data.dto.user.TokenResponse;
import com.c9Pay.userservice.interceptor.GatewayValidation;
import com.c9Pay.userservice.web.docs.LoginControllerDocs;
import com.c9Pay.userservice.web.mvc.service.UserService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.c9Pay.userservice.constant.BearerConstant.BEARER_PREFIX;
import static com.c9Pay.userservice.constant.CookieConstant.AUTHORIZATION_HEADER;
import static com.c9Pay.userservice.constant.ServiceConstant.API;

/**
 * 사용자 로그인을 처리하는 컨트롤러.
 *
 */
@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class LoginController implements LoginControllerDocs {
    private final UserService userService;


    /**
     * 사용자 로그인을 처리한다.
     *
     * @param form 사용자 로그인 정보를 담고 있는 LoginForm 객체
     * @param response HTTP 응답 객체
     * @return 로그인 성공 시 성공 메세지를 담은 ResponseEntity 반환
     */
    @Override
    @GatewayValidation(API)
    @PostMapping(value = "/api/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@Valid @RequestBody LoginForm form, HttpServletResponse response){
        String token = userService.authenticate(form.getUserId(), form.getPassword());
        TokenResponse tokenBody = new TokenResponse(BEARER_PREFIX + token);
        return ResponseEntity.ok().body(tokenBody);
    }

}
