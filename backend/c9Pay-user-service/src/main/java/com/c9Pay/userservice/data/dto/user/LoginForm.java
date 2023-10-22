package com.c9Pay.userservice.data.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 로그인 폼 데이터를 나타내는 클래스
 */
@Data @AllArgsConstructor
@NoArgsConstructor
@Schema(description = "로그인 폼")
public class LoginForm {

    @NotBlank
    @Schema(description = "회원 아이디", example = "UserId")
    private String userId;
    @NotBlank
    @Schema(description = "회원 비밀번호", example = "q1w2e3r4")
    private String password;
}
