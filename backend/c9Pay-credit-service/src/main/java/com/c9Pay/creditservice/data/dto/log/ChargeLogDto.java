package com.c9Pay.creditservice.data.dto.log;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChargeLogDto {

    public ChargeLogDto(String source, Long amount) {
        this.source = source;
        this.amount = amount;
    }

    @NotBlank
    private String source;

    private String destination;
    @NotNull
    private Long amount;
}
