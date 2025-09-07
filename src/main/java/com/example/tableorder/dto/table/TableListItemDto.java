package com.example.tableorder.dto.table;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TableListItemDto {
    private Long tableId;
    private Integer tableNumber;
    private String status;   // Empty / Occupied / Paying
}
