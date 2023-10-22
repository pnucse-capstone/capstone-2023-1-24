package com.c9Pay.userservice.data.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * 인증 서비스와 통신 중 사용될 개체 식별번호를 담고 있는 데이터 전송 객체 클래스
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SerialNumberResponse {

    @NotNull
    private UUID serialNumber;
}
