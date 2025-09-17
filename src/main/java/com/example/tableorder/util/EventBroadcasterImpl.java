package com.example.tableorder.util;

import org.springframework.stereotype.Component;

/**
 * EventBroadcaster 구현 클래스. - SSE만 사용하므로 WebSocket 기능 비활성화 - 실시간 이벤트는
 * SseRealtimeStatsService를 통해 처리
 */
@Component
public class EventBroadcasterImpl implements EventBroadcaster {

    @Override
    public void publish(String topic) {
        // SSE만 사용하므로 WebSocket 메시지 전송 비활성화
        // 실시간 이벤트는 SseRealtimeStatsService에서 처리
    }

    @Override
    public void publish(String topic, Object data) {
        // SSE만 사용하므로 WebSocket 메시지 전송 비활성화
        // 실시간 이벤트는 SseRealtimeStatsService에서 처리
    }

    @Override
    public void publishToTable(String topic, Object data, Long storeId, Long tableId) {
        // SSE만 사용하므로 WebSocket 메시지 전송 비활성화
        // 실시간 이벤트는 SseRealtimeStatsService에서 처리
    }
}
