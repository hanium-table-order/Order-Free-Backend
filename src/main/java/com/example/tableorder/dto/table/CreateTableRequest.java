package com.example.tableorder.dto.table;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CreateTableRequest {
    @NotNull
    private Long storeId;
    @NotNull
    private Integer tableNumber;
}
