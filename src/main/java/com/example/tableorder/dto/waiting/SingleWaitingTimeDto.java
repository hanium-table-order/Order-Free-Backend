package com.example.tableorder.dto.waiting;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SingleWaitingTimeDto {
    private Long orderId;
    private int expectedWaitMinute;
}
