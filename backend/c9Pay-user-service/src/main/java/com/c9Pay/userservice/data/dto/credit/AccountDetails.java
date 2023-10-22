package com.c9Pay.userservice.data.dto.credit;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 계좌를 정보를 담고있는 데이터 전송 객체 클래스
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetails {

    @NotBlank
    String serialNumber;
    @Min(value = 0)
    Long credit;
}
