package com.example.tableorder.util;

/**
 * 이벤트 브로드캐스트 스텁 인터페이스.
 * - 웹소켓 전송 구현은 범위 외, 토픽 문자열 호출만.
 * - 실제 구현은 별도 (e.g. SimpMessagingTemplate 주입).
 */
public interface EventBroadcaster {

    /**
     * 토픽으로 이벤트 퍼블리시.
     * @param topic 토픽 문자열 (e.g. "ws.inventory.{storeId}.changed")
     */
    void publish(String topic);
}