package com.example.tableorder.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StoreRequestDto {

    @NotBlank
    @Pattern(regexp = "^[0-9]{10}$", message = "사업자 번호는 10자리 숫자여야 합니다.")
    private String businessNumber;

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotBlank
    private String hours;

    private String floorplanUrl;  // nullable
}