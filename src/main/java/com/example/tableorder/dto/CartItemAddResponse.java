package com.example.tableorder.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemAddResponse {
    private Long cartItemId;
    private Long menuItemId;
    private String menuName;
    private Integer quantity;
    private Integer price;
    private Integer linePrice;
}
