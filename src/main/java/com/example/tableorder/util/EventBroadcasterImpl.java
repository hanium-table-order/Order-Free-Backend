package com.example.tableorder.util;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * EventBroadcaster 구현 클래스.
 * - SimpMessagingTemplate으로 /topic/...에 메시지 전송.
 * - 스텁으로 빈 문자열 메시지 (실제 데이터 필요 시 확장).
 */
@Component
public class EventBroadcasterImpl implements EventBroadcaster {

    private final SimpMessagingTemplate messagingTemplate;

    public EventBroadcasterImpl(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void publish(String topic) {
        messagingTemplate.convertAndSend("/topic/" + topic, "");  // 빈 메시지, 필요 시 데이터 추가
    }
}