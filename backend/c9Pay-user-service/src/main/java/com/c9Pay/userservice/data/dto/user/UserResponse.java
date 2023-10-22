package com.c9Pay.userservice.data.dto.user;

import com.c9Pay.userservice.data.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;


/**
 * 사용자 정보 응답 데이터 전송 객체를 나타내는 클래스
 */
@Data
@Schema(description = "사용자 정보 응답 형식")
@Builder
public class UserResponse {
    @NotBlank
    @Schema(description = "회원 이름", example = "홍길동")
    private String username;
    @NotBlank
    @Schema(description = "회원 아이디", example = "UserId")
    private String userId;
    @NotBlank
    @Email
    @Schema(description = "회원 이메일", example = "email@example.com")
    private String email;
    @Min(0)
    @Schema(description = "충전된 크레딧 양", example = "15000")
    private Long credit;

    /**
     * User 엔티티 객체를 UserResponse 객체로 매핑하여 반환한다.
     *
     * @param user User 엔티티 객체
     * @return 매핑된 UserResponse 객체
     */
    public static UserResponse mapping(User user, Long credit){
        return UserResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .username(user.getUsername())
                .credit(credit)
                .build();
    }
}
