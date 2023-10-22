package com.c9Pay.userservice.web.docs;

import com.c9Pay.userservice.data.dto.user.LoginForm;
import com.c9Pay.userservice.data.dto.user.TokenResponse;
import com.c9Pay.userservice.data.dto.user.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Tag(name = "로그인")
public interface LoginControllerDocs {

    @Operation(summary = "로그인 요청")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "로그인 성공",headers = @Header(name=AUTHORIZATION, description = "Access 토큰"),content = @Content(schema = @Schema(implementation = TokenResponse.class))),
                    @ApiResponse(responseCode = "400", description = "로그인 실패", content = @Content(schema = @Schema(type= MediaType.TEXT_PLAIN_VALUE, example = "{\n \"errorCode\": \"101\"\n}")))
            }
    )
    ResponseEntity<?> login(LoginForm form, HttpServletResponse response);
}
