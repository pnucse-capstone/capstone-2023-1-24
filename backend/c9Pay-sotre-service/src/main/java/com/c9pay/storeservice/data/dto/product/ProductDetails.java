package com.c9pay.storeservice.data.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetails {
    private long id;
    private String name;
    private int price;
    private String imageUrl;
}
