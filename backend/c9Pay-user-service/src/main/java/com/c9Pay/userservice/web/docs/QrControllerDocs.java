package com.c9Pay.userservice.web.docs;

import com.c9Pay.userservice.data.dto.auth.ExchangeToken;
import com.c9Pay.userservice.data.dto.user.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Tag(name = "Qr 생성")
public interface QrControllerDocs {
    @Operation(summary = "QR 생성 요청")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "qr 생성 토큰 값", content = @Content(schema = @Schema(implementation = ExchangeToken.class))),
                    @ApiResponse(responseCode = "400", description = "로그인 필요", content = @Content(schema = @Schema(type= MediaType.TEXT_PLAIN_VALUE, example = "{\n \"errorCode\": \"101\"\n}")))
            }
    )
    ResponseEntity<?>createQr(String token);
}
