package com.c9pay.storeservice.data.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductForm {
    private String name;
    private int price;
    private String imageUrl;
}
