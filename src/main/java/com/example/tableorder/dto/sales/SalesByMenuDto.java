package com.example.tableorder.dto.sales;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SalesByMenuDto {
    private Long menuId;
    private String name;
    private Long qty;          // 총 판매 수량
    private Long totalSales;   // 총 매출(원)
}
