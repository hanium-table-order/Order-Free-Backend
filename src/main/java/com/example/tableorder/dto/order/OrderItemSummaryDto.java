package com.example.tableorder.dto.order;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderItemSummaryDto {
    private Long menuId;
    private String name;      // OrderItem.menuName 우선
    private Integer qty;
    private Integer unitPrice;
}
