package com.c9Pay.userservice.data.dto.user;

import com.c9Pay.userservice.data.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * 사용자 정보 데이터 전송 객체를 나타내는 클래스
 */
@Data
@AllArgsConstructor
@Builder
@Schema(description = "사용자 DTO")
public class UserDto {

    @Schema(description = "회원이름", example = "홍길동")
    @NotBlank
    private String username;

    @NotBlank
    @Schema(description = "회원 아이디", example = "userId")
    private String userId;

    @NotBlank
    @Schema(description = "회원 비밀번호", example = "q1w2e3r4")
    private String password;

    @NotBlank
    @Email
    @Schema(description = "회원 이메일", example = "exam@example.com")
    private String email;

    /**
     * UserDto 객체를 User 엔티티 객체로 변환하여 반환한다.
     *
     * @param serialNumber 사용자의 개체식별번호 (serialNumber)
     * @return 변환된 User 엔티티 객체
     */
    public User toEntity(UUID serialNumber){
        return User.builder()
                .userId(userId)
                .password(password)
                .username(username)
                .serialNumber(serialNumber)
                .email(email).build();
    }
}
