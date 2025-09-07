package com.example.tableorder.dto.sales;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SalesByTableDto {
    private Long tableId;
    private Long totalSales;
}
