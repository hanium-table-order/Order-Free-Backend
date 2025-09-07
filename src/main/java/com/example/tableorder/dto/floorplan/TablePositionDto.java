package com.example.tableorder.dto.floorplan;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TablePositionDto {
    private Long tableId;
    private Integer coordX;
    private Integer coordY;
}
