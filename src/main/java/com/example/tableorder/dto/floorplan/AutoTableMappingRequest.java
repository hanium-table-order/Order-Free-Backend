package com.example.tableorder.dto.floorplan;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AutoTableMappingRequest {

    @NotNull
    @JsonProperty("store_id")
    private Long storeId;

    @NotNull
    @JsonProperty("table_layout")
    private List<TableLayoutItem> tableLayout;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class TableLayoutItem {
        @JsonProperty("table_id")
        private Long tableId;
        @JsonProperty("coord_x")
        private Integer coordX;
        @JsonProperty("coord_y")
        private Integer coordY;
    }
}
