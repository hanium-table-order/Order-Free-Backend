package com.example.tableorder.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * 점주 메뉴 등록/수정 요청 DTO.
 * - Map 기반 다국어 지원 (name, description).
 * - 옵션 List<OptionDto> 포함.
 */
@Getter
@Setter
@Builder
public class AdminMenuRequestDto {

    @NotNull
    @PositiveOrZero
    private Long categoryId;  // String → Long으로 변경

    @NotNull
    private Map<String, String> name;

    @PositiveOrZero
    private Integer price;  // int → Integer

    private Map<String, String> description; // @NotNull 제거, 선택으로

    private List<OptionDto> options;

    private String image;

    private Boolean soldOut;

    @PositiveOrZero
    private Integer quantity;  // int → Integer

    private Boolean enableInventory;

    /**
     * 옵션 내부 DTO.
     */
    @Getter
    @Setter
    @Builder
    public static class OptionDto {

        @PositiveOrZero
        private Integer extraPrice;  // int → Integer

        private Boolean required;

        @NotNull
        private Map<String, String> name;

        private Map<String, String> description;  // 선택
    }
}