package com.example.tableorder.util;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * EventBroadcaster 구현 클래스. - SimpMessagingTemplate으로 /topic/...에 메시지 전송. - 실시간
 * 이벤트 브로드캐스트 지원.
 */
@Component
public class EventBroadcasterImpl implements EventBroadcaster {

    private final SimpMessagingTemplate messagingTemplate;

    public EventBroadcasterImpl(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void publish(String topic) {
        messagingTemplate.convertAndSend("/topic/" + topic, "");
    }

    @Override
    public void publish(String topic, Object data) {
        messagingTemplate.convertAndSend("/topic/" + topic, data);
    }

    @Override
    public void publishToTable(String topic, Object data, Long storeId, Long tableId) {
        String tableTopic = String.format("/topic/store.%d.table.%d.%s", storeId, tableId, topic);
        messagingTemplate.convertAndSend(tableTopic, data);
    }
}
