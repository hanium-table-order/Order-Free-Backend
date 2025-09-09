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
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Optional;

/**
 * 웹소켓 이벤트 리스너 - 사용자 연결/해제 시 접속자 수 업데이트
 */
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final RealtimeStatsService realtimeStatsService;
    private final StoreRepository storeRepository;
    private final StoreTableRepository storeTableRepository;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        System.out.println("웹소켓 연결됨");

        // 헤더에서 매장/테이블 정보 추출 시도
        Long storeId = null;
        Long tableId = null;

        // 간단하게 기본값 사용 (인터셉터 제거로 인해)
        storeId = getFirstStoreId();
        tableId = getFirstTableId(storeId);
        System.out.println("기본값 사용 - 매장: " + storeId + ", 테이블: " + tableId);

        System.out.println("사용자 연결 - 매장: " + storeId + ", 테이블: " + tableId);
        realtimeStatsService.userConnected(storeId, tableId);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        System.out.println("웹소켓 연결 해제됨");

        // 헤더에서 매장/테이블 정보 추출 시도
        Long storeId = null;
        Long tableId = null;

        try {
            StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
            String storeIdStr = headerAccessor.getFirstNativeHeader("storeId");
            String tableIdStr = headerAccessor.getFirstNativeHeader("tableId");

            if (storeIdStr != null && tableIdStr != null) {
                storeId = Long.parseLong(storeIdStr);
                tableId = Long.parseLong(tableIdStr);
                System.out.println("헤더에서 추출된 매장/테이블 정보 - 매장: " + storeId + ", 테이블: " + tableId);
            } else {
                // 헤더에서 정보를 가져올 수 없으면 기본값 사용
                storeId = getFirstStoreId();
                tableId = getFirstTableId(storeId);
                System.out.println("기본값 사용 - 매장: " + storeId + ", 테이블: " + tableId);
            }
        } catch (Exception e) {
            // 오류 발생 시 기본값 사용
            storeId = getFirstStoreId();
            tableId = getFirstTableId(storeId);
            System.out.println("오류로 인한 기본값 사용 - 매장: " + storeId + ", 테이블: " + tableId);
        }

        System.out.println("사용자 연결 해제 - 매장: " + storeId + ", 테이블: " + tableId);
        realtimeStatsService.userDisconnected(storeId, tableId);
    }

    /**
     * DB에서 첫 번째 매장 ID를 가져옴
     */
    private Long getFirstStoreId() {
        Optional<Store> firstStore = storeRepository.findAll().stream().findFirst();
        if (firstStore.isPresent()) {
            return firstStore.get().getId();
        }
        // DB에 매장이 없으면 기본값 1L 반환
        return 1L;
    }

    /**
     * DB에서 첫 번째 테이블 ID를 가져옴
     */
    private Long getFirstTableId(Long storeId) {
        Optional<StoreTable> firstTable = storeTableRepository.findAll().stream()
                .filter(table -> table.getStore().getId().equals(storeId))
                .findFirst();
        if (firstTable.isPresent()) {
            return firstTable.get().getId();
        }
        // DB에 테이블이 없으면 기본값 1L 반환
        return 1L;
    }
}
