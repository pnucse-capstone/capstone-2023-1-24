package com.c9Pay.userservice.data.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "사용자 개체식별 번호")
public class TokenResponse {
    @Schema(description = "사용자 개체식별 번호", example = "da3f3044-a7c1-4cbd-975e-d099b1aeb514")
    private String token;
}
