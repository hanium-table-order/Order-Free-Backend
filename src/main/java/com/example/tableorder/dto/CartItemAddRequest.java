package com.example.tableorder.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemAddRequest {
    private Long menuItemId;
    private Integer quantity;
    private String lang;
}
