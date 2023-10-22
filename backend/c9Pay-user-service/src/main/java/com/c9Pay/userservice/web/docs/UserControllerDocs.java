package com.c9Pay.userservice.web.docs;

import com.c9Pay.userservice.data.dto.user.UserDto;
import com.c9Pay.userservice.data.dto.user.UserResponse;
import com.c9Pay.userservice.data.dto.user.UserUpdateParam;
import com.c9Pay.userservice.web.exception.handler.ExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;

@Tag(name = "사용자 관리")
public interface UserControllerDocs {

    @Operation(summary = "회원가입 요청")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "회원가입 성공 (반환 값 없음)", content = @Content),
                    @ApiResponse(responseCode = "400", description = "회원가입 실패", content = @Content(schema = @Schema(type=MediaType.TEXT_PLAIN_VALUE, example = "{\n \"errorCode\": \"101\"\n}")))
            }
    )
    ResponseEntity<?> signUp(UserDto form);

    @Operation(summary = "사용자 개인 정보 요청")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "회원 정보", content = @Content(schema = @Schema(implementation = UserResponse.class))),
                    @ApiResponse(responseCode = "400", description = "로그인 필요", content = @Content(schema = @Schema(type=MediaType.TEXT_PLAIN_VALUE, example = "{\n \"errorCode\": \"101\"\n}")))
            }
    )
    ResponseEntity<?> getUserDetail(String token);

    @Operation(summary = "회원 탈퇴 요청")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "회원 삭제(반환 값 없음)", content = @Content),
                    @ApiResponse(responseCode = "400", description = "로그인 필요", content = @Content(schema = @Schema(type=MediaType.TEXT_PLAIN_VALUE, example = "{\n \"errorCode\": \"101\"\n}")))
            }
    )
    ResponseEntity<?> deleteUser(String token, HttpServletResponse response);

    @Operation(summary = "회원 정보 수정 요청")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "회원 정보 수정", content = @Content(schema = @Schema(implementation = UserUpdateParam.class))),
                    @ApiResponse(responseCode = "400", description = "로그인 필요", content = @Content(schema = @Schema(type=MediaType.TEXT_PLAIN_VALUE, example = "{\n \"errorCode\": \"101\"\n}")))
            }
    )
    ResponseEntity<?> updateUserInfo(String token, UserUpdateParam param, HttpServletResponse response);

    @Operation(summary = "회원 개체식별번호 요청")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "회원 개체식별번호 요청",
                            content = @Content(schema = @Schema(type=MediaType.TEXT_PLAIN_VALUE, example = "{\n \"serialNumber\": \"8307984e-211a-423a-923f-ea19644485b4\"\n}"))),
                    @ApiResponse(responseCode = "400", description = "로그인 필요", content = @Content(schema = @Schema(type=MediaType.TEXT_PLAIN_VALUE, example = "{\n \"errorCode\": \"101\"\n}")))
            }
    )
    ResponseEntity<?> getSerialNumber(String token);

    @Operation(summary = "아이디 중복 확인 요청")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "아이디 중복 안됨(반환 값 없음)", content = @Content),
                    @ApiResponse(responseCode = "400", description = "아이디 중복 됨(반환 값 없음)", content = @Content)
            }
    )
    ResponseEntity<?> checkDuplicated(String userId);
}
