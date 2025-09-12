package com.example.tableorder.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class OrderRequestDto {

    @NotNull
    @Positive
    private Long tableId;

    @NotEmpty
    private List<Item> items;

    @Getter
    @Setter
    @Builder
    public static class Item {
        @NotNull
        @Positive
        private Long menuItemId;

        @NotNull
        @Positive
        private Integer quantity;

        private List<Long> optionIds;  // nullable
    }
}