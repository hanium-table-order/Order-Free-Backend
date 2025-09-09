package com.example.tableorder.dto.table;

import java.time.LocalDateTime;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateTableStatusResponse {
    private Long tableId;
    private String status;
    private LocalDateTime updatedAt;
}
