package com.example.tableorder.dto.order;

import java.time.LocalDateTime;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LiveOrderDto {
    private Long orderId;
    private Long tableId;
    private String status;
    private LocalDateTime orderedAt; // Order.createdAt
    private Long totalAmount;     // sum(unitPrice * quantity)
}
