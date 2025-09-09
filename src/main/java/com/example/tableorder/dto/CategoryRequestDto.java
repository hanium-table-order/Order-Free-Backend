package com.example.tableorder.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 카테고리 추가 요청 DTO.
 * - 다국어 이름 필수 (@NotBlank).
 */
@Getter
@Setter
@Builder
public class CategoryRequestDto {

    @NotBlank
    private String nameKo;

    @NotBlank
    private String nameEn;

    @NotBlank
    private String nameZh;

    @NotBlank
    private String nameJa;
}