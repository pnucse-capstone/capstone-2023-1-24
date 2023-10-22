package com.c9pay.authservice.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeToken {
    private String content;
    private Long expiredAt;
    private String sign;
}
