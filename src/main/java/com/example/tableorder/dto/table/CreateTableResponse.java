package com.example.tableorder.dto.table;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateTableResponse {
    private Long tableId;
    private Long storeId;
    private Integer tableNumber;
    private String status; // 기본 Empty
}
