package com.c9Pay.creditservice.web.docs;

import com.c9Pay.creditservice.data.dto.charge.ChargeAmount;
import com.c9Pay.creditservice.data.dto.charge.ChargeForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "거래 관련")
public interface ChangeControllerDocs {

    @Operation(summary="입금")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "입금 성공(반환 값 없음)", content = @Content),
                    @ApiResponse(responseCode = "400", description = "입금 실패(반환 값 없음)", content = @Content)
            }
    )
    ResponseEntity<?> loadCredit(String identifier,ChargeForm form, HttpServletRequest request);


    @Operation(summary="송금")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "크레딧 송금 성공",
                            content = @Content(schema = @Schema(type= MediaType.TEXT_PLAIN_VALUE, example = "transfer successful"))),
                    @ApiResponse(responseCode = "400", description = "크레딧 송금 실패(반환 값 없음)", content = @Content)
            }
    )
    ResponseEntity<?> transfer(String to, String from, ChargeAmount chargeAmount);
}
