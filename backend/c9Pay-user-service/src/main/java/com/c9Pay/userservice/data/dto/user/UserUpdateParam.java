package com.c9Pay.userservice.data.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 사용자 정보 업데이트를 위한 요청 데이터 전송 객체를 나타내는 클래스
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "회원 정보 수정 폼")
@Data
public class UserUpdateParam {
    @Schema(description = "사용자 이름", example = "홍길동")
    @NotBlank
    String username;

    @Schema(description = "비밀번호", example = "q1w2e3r4")
    @NotBlank
    String password;

    @Schema(description = "회원 이메일", example = "exam@example.com")
    @Email
    @NotBlank
    String email;

    @Schema(description = "회원 아이디", example = "UserId")
    @NotBlank
    String userId;
}
