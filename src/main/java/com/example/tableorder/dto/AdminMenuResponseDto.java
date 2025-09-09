package com.example.tableorder.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * 점주 메뉴 응답 DTO.
 * - Map 기반 다국어 지원 (name, description).
 * - 옵션 List<OptionDto> 포함.
 */
@Getter
@Setter
@Builder
public class AdminMenuResponseDto {

    private String menuId;

    private String categoryId;

    private Map<String, String> name;

    private int price;

    private Map<String, String> description;

    private List<OptionDto> options;

    private String image;

    private Boolean soldOut;

    private int quantity;

    private Boolean enableInventory;

    private String message;  // 선택 메시지

    /**
     * 옵션 내부 DTO.
     */
    @Getter
    @Setter
    @Builder
    public static class OptionDto {

        private String id;

        private int extraPrice;

        private Boolean required;

        private Map<String, String> name;

        private Map<String, String> description;  // 선택, but 에러 해결을 위해 추가
    }
}