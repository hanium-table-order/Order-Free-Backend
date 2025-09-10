package com.example.tableorder.dto.realtime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewerCountEvent {
    private Long storeId;
    private Integer viewerCount;
    private String message; // "현재 3명이 메뉴를 보고 있습니다"
    private Long timestamp;
}
