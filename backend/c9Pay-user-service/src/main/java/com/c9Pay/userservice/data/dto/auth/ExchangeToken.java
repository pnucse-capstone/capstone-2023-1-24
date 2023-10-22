package com.c9Pay.userservice.data.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 인증서비스에서 받아올 사용자에게 응답될 토큰 정보를 담는 클래스
 */
@Schema(description = "QR 생성을 위한 토큰 정보")
@Data@AllArgsConstructor@NoArgsConstructor
public class ExchangeToken {
    @Schema(description = "내용")
    private String content;
    @Schema(description = "만료 시간")
    private Long expiredAt;
    @Schema(description = "서명")
    private String sign;
}
