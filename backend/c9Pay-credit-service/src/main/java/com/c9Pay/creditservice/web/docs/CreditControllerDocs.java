package com.c9Pay.creditservice.web.docs;

import com.c9Pay.creditservice.data.dto.charge.AccountDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.TableGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "계좌 생성/삭제")
public interface CreditControllerDocs {

    @Operation(summary="계좌 생성")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "계좌 생성 성공(반환 값 없음)", content = @Content),
                    @ApiResponse(responseCode = "400", description = "계좌 생성 실패(반환 값 없음)", content = @Content)
            }
    )
    ResponseEntity<?> createAccount(String serialNumber);


    @Operation(summary="계좌 삭제")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "계좌 삭제 성공(반환 값 없음)", content = @Content),
                    @ApiResponse(responseCode = "400", description = "계좌 삭제 실패(반환 값 없음)", content = @Content)
            }
    )
    ResponseEntity<?> deleteAccount(String serialNumber);

    @Operation(summary="계좌 조회")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "계좌 정보 조회 성공",
                            content = @Content(schema = @Schema(implementation = AccountDetails.class))),
                    @ApiResponse(responseCode = "400", description = "아이디 중복 됨(반환 값 없음)", content = @Content)
            }
    )
    ResponseEntity<AccountDetails> getAccount(String serialNumber);
}
