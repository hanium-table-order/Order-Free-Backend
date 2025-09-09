package com.example.tableorder.dto.realtime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 메뉴 품절 실시간 이벤트 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuOutOfStockEvent {

    private Long storeId;
    private Long menuId;
    private String menuName;
    private String message; // "오늘 김치찌개가 품절되었습니다"
    private Long timestamp;
}
