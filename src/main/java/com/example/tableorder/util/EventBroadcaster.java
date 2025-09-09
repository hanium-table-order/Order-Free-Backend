package com.example.tableorder.util;

/**
 * 이벤트 브로드캐스트 인터페이스. - 웹소켓을 통한 실시간 이벤트 전송 - 장바구니, 주문, 재고 등의 실시간 알림 지원
 */
public interface EventBroadcaster {

    /**
     * 토픽으로 이벤트 퍼블리시 (빈 메시지).
     *
     * @param topic 토픽 문자열 (e.g. "cart.item.added")
     */
    void publish(String topic);

    /**
     * 토픽으로 이벤트 퍼블리시 (데이터 포함).
     *
     * @param topic 토픽 문자열
     * @param data 전송할 데이터 객체
     */
    void publish(String topic, Object data);

    /**
     * 특정 테이블에만 이벤트 전송.
     *
     * @param topic 토픽 문자열
     * @param data 전송할 데이터 객체
     * @param storeId 매장 ID
     * @param tableId 테이블 ID
     */
    void publishToTable(String topic, Object data, Long storeId, Long tableId);
}
