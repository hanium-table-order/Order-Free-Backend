package com.example.tableorder.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderItemDetail {
    private MenuItemResponse menu;
    private Integer quantity;
    private List<MenuOptionResponse> options;
    private Integer subtotal;
}
