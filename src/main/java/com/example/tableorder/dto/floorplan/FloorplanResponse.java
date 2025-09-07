package com.example.tableorder.dto.floorplan;

import java.util.List;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FloorplanResponse {
    private String floorplanUrl;
    private List<TablePositionDto> tables;
}
