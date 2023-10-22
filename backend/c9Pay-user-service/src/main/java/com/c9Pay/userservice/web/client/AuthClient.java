package com.c9Pay.userservice.web.client;

import com.c9Pay.userservice.data.dto.auth.ExchangeToken;
import com.c9Pay.userservice.data.dto.auth.SerialNumberResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 인증 서비스와 통신하기 위한 Feign 클라이언트 인터페이스
 */
@FeignClient(name="auth-service")
public interface AuthClient {

    /**
     * 개체식별번호 생성을 요청하기 위한 API 호출이다.
     *
     * @return SerialNumberResponse 객체를 포함하는 ResponseEntity 반환
     */
    @PostMapping("/auth-service/serial-number")
    ResponseEntity<SerialNumberResponse> getSerialNumber();

    /**
     * 특정 개체식별번호로 QR 코드를 생성하기 위한 API 호출이다.
     *
     * @param serialNumber 개체식별번호
     * @return ExchangeToken 객체를 포함하는 ResponseEntity 반환
     */
    @GetMapping("/auth-service/serial-number/{serialNumber}/exchange")
    ResponseEntity<ExchangeToken> createQR(@PathVariable String serialNumber);


    /**
     * 특정 개체식별번호의 유효성을 검증하기 위한 API 호출이다.
     *
     * @param serialNumber 개체식별번호
     * @return 유효성 검증 결과를 포함하는 ResponseEntity 반환
     */
    @GetMapping("/auth-service/serial-number/{serialNumber}")
    ResponseEntity<?> validateSerialNumber(@PathVariable String serialNumber);
}
