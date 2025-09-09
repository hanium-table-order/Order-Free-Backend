package com.example.tableorder.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StoreResponseDto {

    private Long id;

    private String businessNumber;

    private String name;

    private String address;

    private String hours;

    private String floorplanUrl;
}