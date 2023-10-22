package com.c9pay.storeservice.data.dto.sale;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSaleInfo {
    private long productId;
    private String name;
    private int price;
    private int amount;
}
