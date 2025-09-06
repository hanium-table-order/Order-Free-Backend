package com.example.tableorder.dto.order;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UpdateOrderStatusRequest {
    @NotBlank
    private String status; // 예: Received / Preparing / Done / Cancelled
}
