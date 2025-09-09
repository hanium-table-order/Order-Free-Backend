package com.example.tableorder.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * WebSocket 핸드셰이크 인터셉터 - 연결 시 매장/테이블 정보를 세션에 저장
 */
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
            WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        // URL 파라미터에서 매장/테이블 정보 추출
        String query = request.getURI().getQuery();
        if (query != null) {
            String[] params = query.split("&");
            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    if ("storeId".equals(keyValue[0])) {
                        attributes.put("storeId", Long.parseLong(keyValue[1]));
                    } else if ("tableId".equals(keyValue[1])) {
                        attributes.put("tableId", Long.parseLong(keyValue[1]));
                    }
                }
            }
        }

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
            WebSocketHandler wsHandler, Exception exception) {
        // 핸드셰이크 완료 후 처리
    }
}
