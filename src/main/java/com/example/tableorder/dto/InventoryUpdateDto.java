package com.example.tableorder.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 재고 업데이트 요청 DTO.
 */
@Getter
@Setter
@Builder
public class InventoryUpdateDto {

    @NotNull
    private Integer delta;  // quantity → delta, int → Integer (음수 허용)

    private Boolean enableInventory;
}