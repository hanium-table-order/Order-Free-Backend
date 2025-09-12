package com.example.tableorder.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderItemResponseDto {

    private Long id;

    private Long menuItemId;

    private Integer quantity;

    private Integer unitPrice;

    private String menuName;
}