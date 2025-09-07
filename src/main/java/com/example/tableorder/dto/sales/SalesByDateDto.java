package com.example.tableorder.dto.sales;

import java.time.LocalDate;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SalesByDateDto {
    private LocalDate date;
    private Long totalSales;   // 원
    private Long orderCount;
}
