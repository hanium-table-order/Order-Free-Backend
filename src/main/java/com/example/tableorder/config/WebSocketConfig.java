package com.example.tableorder.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocket 설정 클래스. - Stomp 프로토콜 지원. - 엔드포인트 /ws, 브로커 /topic.
 *
 * @deprecated SSE 방식으로 대체됨. 접속자 수 조회는 SseController 사용 권장
 */
@Configuration
// @EnableWebSocketMessageBroker  // SSE 사용으로 인해 비활성화
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();  // SockJS fallback
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");  // 서버 -> 클라이언트
        registry.setApplicationDestinationPrefixes("/app");  // 클라이언트 -> 서버
    }
}
