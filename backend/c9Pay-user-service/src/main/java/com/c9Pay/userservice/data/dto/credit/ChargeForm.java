package com.c9Pay.userservice.data.dto.credit;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;

import java.util.Date;

/**
 * 계좌 송금, 충전을 위한 Credit 정보를 담는 전송 객체 클래스
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "크레딧 충전 폼")
public class ChargeForm {

    @Min(value = 0)
    @Schema(description = "크레딧 충전 액수", example = "15000")
    private Long quantity;

}
