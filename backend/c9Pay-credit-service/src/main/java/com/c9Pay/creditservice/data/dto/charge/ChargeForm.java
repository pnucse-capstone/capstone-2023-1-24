package com.c9Pay.creditservice.data.dto.charge;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChargeForm {
    @Min(0)
    private Long quantity;


}
