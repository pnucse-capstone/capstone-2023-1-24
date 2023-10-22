package com.c9Pay.userservice.web.docs;

import com.c9Pay.userservice.data.dto.credit.ChargeForm;
import com.c9Pay.userservice.data.dto.user.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Tag(name = "크레딧 입금/조회")
public interface CreditControllerDocs{

    @Operation(summary = "크레딧 충전 요청")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "크레딧 충전 성곰(반환 값 없음)", content = @Content),
                    @ApiResponse(responseCode = "400", description = "크레딧 충전 실패", content=@Content)
            }
    )
    ResponseEntity<?> chargeCredit(ChargeForm form, String token);

    @Operation(summary = "회원 크레딧 조회")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "회원 정보", content = @Content(schema = @Schema(implementation = ChargeForm.class))),
                    @ApiResponse(responseCode = "400", description = "로그인 필요", content = @Content(schema = @Schema(type= MediaType.TEXT_PLAIN_VALUE, example = "{\n \"errorCode\": \"101\"\n}")))
            }
    )
    ResponseEntity<?> getCredit(String token);
}
