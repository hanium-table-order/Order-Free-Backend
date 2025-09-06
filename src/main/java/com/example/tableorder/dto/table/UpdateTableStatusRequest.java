package com.example.tableorder.dto.table;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UpdateTableStatusRequest {
    @NotBlank
    private String status; // Empty / Occupied / Paying
}
