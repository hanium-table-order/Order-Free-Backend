package com.example.tableorder.dto.floorplan;

import java.time.LocalDateTime;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateTablePositionResponse {
    private Long tableId;
    private Integer coordX;
    private Integer coordY;
    private LocalDateTime updatedAt;
}
