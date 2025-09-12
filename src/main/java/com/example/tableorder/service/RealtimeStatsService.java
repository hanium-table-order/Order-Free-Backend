package com.example.tableorder.service;

import com.example.tableorder.dto.realtime.OrderStatusChangedEvent;
import com.example.tableorder.dto.realtime.ViewerCountEvent;
import com.example.tableorder.entity.store.Store;
import com.example.tableorder.entity.store.StoreTable;
import com.example.tableorder.repository.StoreRepository;
import com.example.tableorder.repository.StoreTableRepository;
import com.example.tableorder.util.EventBroadcaster;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Map;

/**
 * 실시간 접속자 수 서비스 - 메뉴판을 보고 있는 사용자 수를 실시간으로 관리
 */
@Service
@RequiredArgsConstructor
public class RealtimeStatsService {

    private final EventBroadcaster eventBroadcaster;
    private final StoreRepository storeRepository;
    private final StoreTableRepository storeTableRepository;

    // 매장별 접속자 수 관리 (매장 ID -> 접속자 수)
    private final Map<Long, AtomicInteger> storeViewerCounts = new ConcurrentHashMap<>();

    /**
     * 사용자가 메뉴판에 접속했을 때 호출
     */
    public void userConnected(Long storeId, Long tableId) {
        // 매장별 접속자 수 증가
        AtomicInteger storeCount = storeViewerCounts.computeIfAbsent(storeId, k -> new AtomicInteger(0));
        int newCount = storeCount.incrementAndGet(); // 접속자 수 +1
        System.out.println("사용자 접속 - 매장 " + storeId + " 현재 접속자 수: " + newCount);

        // 즉시 접속자 수 브로드캐스트 (매장 전체)
        broadcastStoreViewerCount(storeId, newCount);
    }

    /**
     * 사용자가 메뉴판에서 나갔을 때 호출
     */
    public void userDisconnected(Long storeId, Long tableId) {
        // 매장별 접속자 수 감소
        AtomicInteger storeCount = storeViewerCounts.get(storeId);
        if (storeCount != null) {
            int newCount = storeCount.decrementAndGet();
            if (newCount < 0) {
                newCount = 0;
                storeCount.set(0);
            }
            System.out.println("사용자 접속 해제 - 매장 " + storeId + " 현재 접속자 수: " + newCount);

            // 즉시 접속자 수 브로드캐스트 (매장 전체)
            broadcastStoreViewerCount(storeId, newCount);
        }
    }

    /**
     * 매장 전체 접속자 수 브로드캐스트
     */
    private void broadcastStoreViewerCount(Long storeId, int count) {
        ViewerCountEvent viewerCountEvent = ViewerCountEvent.builder()
                .storeId(storeId)
                        .viewerCount(count)
                                .message("현재 " + count + "명이 메뉴를 보고 있습니다")
                                        .timestamp(System.currentTimeMillis())
                                                .build();

        // 매장 전체 토픽으로 전송
        eventBroadcaster.publish("store." + storeId + ".viewer.count", viewerCountEvent);
    }

    /**
     * DB에서 첫 번째 매장 ID를 가져옴 (public)
     */
    public Long getFirstStoreId() {
        Optional<Store> firstStore = storeRepository.findAll().stream().findFirst();
        if (firstStore.isPresent()) {
            return firstStore.get().getId();
        }
        // DB에 매장이 없으면 기본값 1L 반환
        return 1L;
    }

    /**
     * DB에서 첫 번째 테이블 ID를 가져옴 (public)
     */
    public Long getFirstTableId(Long storeId) {
        Optional<StoreTable> firstTable = storeTableRepository.findAll().stream()
                .filter(table -> table.getStore().getId().equals(storeId))
                .findFirst();
        if (firstTable.isPresent()) {
            return firstTable.get().getId();
        }
        // DB에 테이블이 없으면 기본값 1L 반환
        return 1L;
    }

    /**
     * 현재 접속자 수 조회 (매장별)
     */
    public int getCurrentViewerCount(Long storeId) {
        AtomicInteger storeCount = storeViewerCounts.get(storeId);
        return storeCount != null ? storeCount.get() : 0;
    }

    /**
     * 접속자 수 리셋 (테스트용)
     */
    public ViewerCountEvent resetViewerCount(Long storeId) {
        AtomicInteger storeCount = storeViewerCounts.get(storeId);
        int newCount = 0;
        if (storeCount != null) {
            storeCount.set(newCount);
        }

        ViewerCountEvent event = ViewerCountEvent.builder()
                .storeId(storeId)
                .viewerCount(newCount)
                .message("접속자 수가 0으로 리셋되었습니다")
                .timestamp(System.currentTimeMillis())
                .build();

        // 리셋 직후에도 실시간 브로드캐스트
        broadcastStoreViewerCount(storeId, newCount);

        System.out.println("매장 " + storeId + " 접속자 수 리셋됨");
        return event;
    }

}
