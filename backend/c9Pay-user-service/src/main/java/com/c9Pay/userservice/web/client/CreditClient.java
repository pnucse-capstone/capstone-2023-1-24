package com.c9Pay.userservice.web.client;

import com.c9Pay.userservice.data.dto.credit.AccountDetails;
import com.c9Pay.userservice.data.dto.credit.ChargeForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * 크레딧 서비스와 통신하기 위한 Feign 클라이언트 인터페이스
 */
@FeignClient(name = "credit-service")
public interface CreditClient {

    /**
     * 특정 개체식별번호를 가진 계정을 생성하기 위한 API 호출이다.
     *
     * @param identifier 개체식별번호
     * @return 결과를 포함하는 ResponseEntity 반환
     */
    @PostMapping("/credit-service/account/{identifier}")
    ResponseEntity<?> createAccount(@PathVariable("identifier") String identifier);

    /**
     * 특정 개체식별번호를 가진 계정에 크레딧을 충전하기 위한 API 호출이다.
     *
     * @param identifier 개체식별번호
     * @param form       충전할 크레딧 양을 담은 ChargeForm 객체
     * @return 결과를 포함하는 ResponseEntity 반환
     */
    @PostMapping("/credit-service/account/{identifier}/load")
    ResponseEntity<?> loadCredit(@PathVariable("identifier") String identifier, ChargeForm form);

    /**
     * 특정 개체식별번호를 가진 계정을 삭제하기 위한 API 호출이다.
     *
     * @param identifier 개체식별번호
     * @return 결과를 포함하는 ResponseEntity 반환
     */
    @DeleteMapping("/credit-service/account/{identifier}")
    ResponseEntity<?> deleteAccount(@PathVariable("identifier") String identifier);

    /**
     * 특정 개체식별번호를 가진 계정의 상세 정보를 조회하기 위한 API 호출이다.
     *
     * @param identifier 개체식별번호
     * @return 계정 상세 정보를 포함하는 ResponseEntity 반환
     */
    @GetMapping("/credit-service/account/{identifier}")
    ResponseEntity<AccountDetails> getAccount(@PathVariable("identifier") String identifier);
}