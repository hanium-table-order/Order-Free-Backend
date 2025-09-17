package com.example.tableorder.service;

import com.example.tableorder.dto.realtime.ViewerCountEvent;
import com.example.tableorder.entity.store.Store;
import com.example.tableorder.entity.store.StoreTable;
import com.example.tableorder.repository.StoreRepository;
import com.example.tableorder.repository.StoreTableRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * SSE 기반 실시간 접속자 수 서비스 메뉴판을 보고 있는 사용자 수를 SSE로 실시간 관리
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SseRealtimeStatsService {

    private final StoreRepository storeRepository;
    private final StoreTableRepository storeTableRepository;

    // 매장별 접속자 수 관리 (매장 ID -> 접속자 수)
    private final Map<Long, AtomicInteger> storeViewerCounts = new ConcurrentHashMap<>();

    // 매장별 SSE 연결 관리 (매장 ID -> 연결된 SseEmitter 리스트)
    private final Map<Long, CopyOnWriteArrayList<SseEmitter>> storeConnections = new ConcurrentHashMap<>();

    // 연결 상태 체크를 위한 스케줄러
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

    /**
     * SSE 스트림 생성 및 연결 관리
     *
     * @param storeId 매장 ID
     * @return SseEmitter
     */
    public SseEmitter createViewerCountStream(Long storeId) {
        SseEmitter emitter = new SseEmitter(30000L); // 30초 타임아웃

        // 연결 리스트에 추가
        storeConnections.computeIfAbsent(storeId, k -> new CopyOnWriteArrayList<>()).add(emitter);

        // 접속자 수 증가
        AtomicInteger storeCount = storeViewerCounts.computeIfAbsent(storeId, k -> new AtomicInteger(0));
        int newCount = storeCount.incrementAndGet();

        log.info("SSE 연결 생성 - 매장: {}, 현재 접속자 수: {}", storeId, newCount);

        // 연결 완료 시 정리 작업
        emitter.onCompletion(() -> {
            log.info("SSE 연결 완료 - 매장: {}", storeId);
            removeConnection(storeId, emitter);
        });

        // 타임아웃 시 정리 작업
        emitter.onTimeout(() -> {
            log.info("SSE 연결 타임아웃 - 매장: {}", storeId);
            removeConnection(storeId, emitter);
        });

        // 오류 시 정리 작업
        emitter.onError((throwable) -> {
            log.error("SSE 연결 오류 - 매장: {}, 오류: {}", storeId, throwable.getMessage());
            removeConnection(storeId, emitter);
        });

        // 주기적으로 연결 상태 체크 (5초마다)
        scheduleConnectionCheck(storeId, emitter);

        // 즉시 현재 접속자 수 전송
        try {
            ViewerCountEvent event = ViewerCountEvent.builder()
                    .storeId(storeId)
                    .viewerCount(newCount)
                    .message("현재 " + newCount + "명이 메뉴를 보고 있습니다")
                    .timestamp(System.currentTimeMillis())
                    .build();

            emitter.send(SseEmitter.event()
                    .name("viewer-count")
                    .data(event));

            log.info("초기 접속자 수 전송 완료 - 매장: {}, 접속자 수: {}", storeId, newCount);

        } catch (Exception e) {
            log.error("초기 접속자 수 전송 실패 - 매장: {}, 오류: {}", storeId, e.getMessage());
            // 전송 실패 시에도 연결은 유지 (클라이언트에서 3초마다 조회하므로)
        }

        return emitter;
    }

    /**
     * 연결 제거 및 접속자 수 감소
     *
     * @param storeId 매장 ID
     * @param emitter 제거할 SseEmitter
     */
    private void removeConnection(Long storeId, SseEmitter emitter) {
        // 연결 리스트에서 제거
        CopyOnWriteArrayList<SseEmitter> connections = storeConnections.get(storeId);
        if (connections != null) {
            boolean removed = connections.remove(emitter);
            if (!removed) {
                log.warn("SSE 연결 제거 실패 - 이미 제거된 연결일 수 있음: 매장 {}", storeId);
                return;
            }
        }

        // 접속자 수 감소
        AtomicInteger storeCount = storeViewerCounts.get(storeId);
        if (storeCount != null) {
            int newCount = storeCount.decrementAndGet();
            if (newCount < 0) {
                newCount = 0;
                storeCount.set(0);
            }

            log.info("SSE 연결 제거 - 매장: {}, 현재 접속자 수: {}", storeId, newCount);

            // 남은 연결들에게 업데이트된 접속자 수 브로드캐스트
            broadcastViewerCount(storeId, newCount);
        }
    }

    /**
     * 접속자 수 브로드캐스트
     *
     * @param storeId 매장 ID
     * @param count 접속자 수
     */
    public void broadcastViewerCount(Long storeId, int count) {
        CopyOnWriteArrayList<SseEmitter> connections = storeConnections.get(storeId);
        if (connections == null || connections.isEmpty()) {
            return;
        }

        ViewerCountEvent event = ViewerCountEvent.builder()
                .storeId(storeId)
                .viewerCount(count)
                .message("현재 " + count + "명이 메뉴를 보고 있습니다")
                .timestamp(System.currentTimeMillis())
                .build();

        // 연결된 모든 클라이언트에게 전송
        connections.removeIf(emitter -> {
            try {
                emitter.send(SseEmitter.event()
                        .name("viewer-count")
                        .data(event));
                return false; // 성공 시 제거하지 않음
            } catch (IOException e) {
                log.error("SSE 전송 실패 - 매장: {}, 오류: {}", storeId, e.getMessage());
                return true; // 실패 시 연결 제거
            }
        });

        log.info("접속자 수 브로드캐스트 완료 - 매장: {}, 접속자 수: {}, 연결 수: {}",
                storeId, count, connections.size());
    }

    /**
     * 현재 접속자 수 조회
     *
     * @param storeId 매장 ID
     * @return ViewerCountEvent
     */
    public ViewerCountEvent getCurrentViewerCount(Long storeId) {
        AtomicInteger storeCount = storeViewerCounts.get(storeId);
        int count = storeCount != null ? storeCount.get() : 0;

        return ViewerCountEvent.builder()
                .storeId(storeId)
                .viewerCount(count)
                .message("현재 " + count + "명이 메뉴를 보고 있습니다")
                .timestamp(System.currentTimeMillis())
                .build();
    }

    /**
     * 접속자 수 리셋 (테스트용)
     *
     * @param storeId 매장 ID
     * @return ViewerCountEvent
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
        broadcastViewerCount(storeId, newCount);

        log.info("매장 {} 접속자 수 리셋됨", storeId);
        return event;
    }

    /**
     * 특정 매장의 연결 수 조회
     *
     * @param storeId 매장 ID
     * @return 연결된 클라이언트 수
     */
    public int getConnectionCount(Long storeId) {
        CopyOnWriteArrayList<SseEmitter> connections = storeConnections.get(storeId);
        return connections != null ? connections.size() : 0;
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
    public int getCurrentViewerCountInt(Long storeId) {
        AtomicInteger storeCount = storeViewerCounts.get(storeId);
        return storeCount != null ? storeCount.get() : 0;
    }

    /**
     * 연결 상태를 주기적으로 체크하여 끊어진 연결을 정리 (5초마다)
     */
    private void scheduleConnectionCheck(Long storeId, SseEmitter emitter) {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                // 연결이 살아있는지 체크하기 위해 하트비트 전송
                emitter.send(SseEmitter.event()
                        .name("heartbeat")
                        .data("ping"));
            } catch (IOException e) {
                // 연결이 끊어진 경우
                log.info("SSE 연결 상태 체크 - 연결 끊어짐 감지: 매장 {}, 오류: {}", storeId, e.getMessage());
                removeConnection(storeId, emitter);
                // 스케줄러 중단
                throw new RuntimeException("연결 끊어짐으로 스케줄러 중단");
            }
        }, 5, 5, TimeUnit.SECONDS);
    }

}
