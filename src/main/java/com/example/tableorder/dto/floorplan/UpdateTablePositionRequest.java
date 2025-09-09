package com.example.tableorder.dto.floorplan;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UpdateTablePositionRequest {
    @NotNull
    @JsonProperty("coord_x")
    private Integer coordX;
    @NotNull
    @JsonProperty("coord_y")
    private Integer coordY;
}
