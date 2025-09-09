package com.example.tableorder.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 카테고리 응답 DTO.
 * - ID와 다국어 이름 포함.
 */
@Getter
@Setter
@Builder
public class CategoryResponseDto {

    private Long id;

    private Long storeId;

    private String nameKo;

    private String nameEn;

    private String nameZh;

    private String nameJa;
}