package com.example.tableorder.dto.waiting;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderWaitingTimeDto {
    private Long orderId;
    private Long tableId;
    private int expectedWaitMinute;
}
