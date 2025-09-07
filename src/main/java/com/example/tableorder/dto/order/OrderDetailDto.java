package com.example.tableorder.dto.order;

import java.time.LocalDateTime;
import java.util.List;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderDetailDto {
    private Long orderId;
    private Long tableId;
    private String status;
    private LocalDateTime orderedAt; // createdAt
    private List<OrderItemSummaryDto> orderItems;
    private Integer totalAmount;
    private String paymentStatus;    // "Paid" | "Unpaid"
}
