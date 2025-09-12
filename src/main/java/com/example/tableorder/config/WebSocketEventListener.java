package com.example.tableorder.config;

import com.example.tableorder.entity.store.Store;
import com.example.tableorder.entity.store.StoreTable;
import com.example.tableorder.repository.StoreRepository;
import com.example.tableorder.repository.StoreTableRepository;
import com.example.tableorder.service.RealtimeStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * 웹소켓 이벤트 리스너 - 사용자 연결/해제 시 접속자 수 업데이트
 */
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final RealtimeStatsService realtimeStatsService;
    private final StoreRepository storeRepository;
    private final StoreTableRepository storeTableRepository;

    /**
     * 클라이언트가 웹소켓 연결 시 실행 (SessionConnectEvent)
     */
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        System.out.println("웹소켓 연결됨");

        Long storeId;
        Long tableId;

        try {
            // STOMP 헤더에서 storeId, tableId 추출
            StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
            String storeIdStr = accessor.getFirstNativeHeader("storeId");
            String tableIdStr = accessor.getFirstNativeHeader("tableId");

            if (storeIdStr != null && tableIdStr != null) {
                storeId = Long.parseLong(storeIdStr);
                tableId = Long.parseLong(tableIdStr);
                System.out.println("헤더에서 추출된 매장/테이블 정보 - 매장: " + storeId + ", 테이블: " + tableId);
            } else {
                // 헤더 없으면 기본값 사용
                storeId = getFirstStoreId();
                tableId = getFirstTableId(storeId);
                System.out.println("기본값 사용 - 매장: " + storeId + ", 테이블: " + tableId);
            }
        } catch (Exception e) {
            // 오류 시 기본값
            storeId = getFirstStoreId();
            tableId = getFirstTableId(storeId);
            System.out.println("오류로 인한 기본값 사용 - 매장: " + storeId + ", 테이블: " + tableId);
        }

        System.out.println("사용자 연결 - 매장: " + storeId + ", 테이블: " + tableId);
        realtimeStatsService.userConnected(storeId, tableId);
    }

    /**
     * 클라이언트가 웹소켓 연결 해제 시 실행 (SessionDisconnectEvent)
     */
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        System.out.println("웹소켓 연결 해제됨");

        Long storeId;
        Long tableId;

        try {
            // STOMP 헤더에서 storeId, tableId 추출
            StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
            String storeIdStr = accessor.getFirstNativeHeader("storeId");
            String tableIdStr = accessor.getFirstNativeHeader("tableId");

            if (storeIdStr != null && tableIdStr != null) {
                storeId = Long.parseLong(storeIdStr);
                tableId = Long.parseLong(tableIdStr);
                System.out.println("헤더에서 추출된 매장/테이블 정보 - 매장: " + storeId + ", 테이블: " + tableId);
            } else {
                storeId = getFirstStoreId();
                tableId = getFirstTableId(storeId);
                System.out.println("기본값 사용 - 매장: " + storeId + ", 테이블: " + tableId);
            }
        } catch (Exception e) {
            storeId = getFirstStoreId();
            tableId = getFirstTableId(storeId);
            System.out.println("오류로 인한 기본값 사용 - 매장: " + storeId + ", 테이블: " + tableId);
        }

        System.out.println("사용자 연결 해제 - 매장: " + storeId + ", 테이블: " + tableId);
        realtimeStatsService.userDisconnected(storeId, tableId);
    }

    /**
     * DB에서 첫 번째 매장 ID 조회
     */
    private Long getFirstStoreId() {
        return storeRepository.findAll().stream()
                .map(Store::getId)
                .findFirst()
                .orElse(1L);
    }

    /**
     * DB에서 첫 번째 테이블 ID 조회
     */
    private Long getFirstTableId(Long storeId) {
        return storeTableRepository.findAll().stream()
                .filter(table -> table.getStore().getId().equals(storeId))
                .map(StoreTable::getId)
                .findFirst()
                .orElse(1L);
    }
}
