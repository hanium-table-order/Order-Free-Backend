package com.example.tableorder.dto.order;

import java.time.LocalDateTime;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateOrderStatusResponse {
    private Long orderId;
    private String status;
    private LocalDateTime updatedAt;
}
