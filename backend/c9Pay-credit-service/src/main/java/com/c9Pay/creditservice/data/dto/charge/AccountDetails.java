package com.c9Pay.creditservice.data.dto.charge;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "계좌 조회 응답 형식")
@AllArgsConstructor
public class AccountDetails {

    @Schema(description = "계좌 크레딧 금액")
    @Min(0)
    Long credit;

    @Schema(description = "개인 식별 번호")
    @NotBlank
    String serialNumber;
}
