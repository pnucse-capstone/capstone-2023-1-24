package com.c9Pay.creditservice.data.dto.charge;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChargeAmount {
    @Min(0)
    private Long creditAmount;
}
