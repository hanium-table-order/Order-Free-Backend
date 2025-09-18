package com.example.tableorder.service;

import com.example.tableorder.dto.StaffCallTypeRequest;
import com.example.tableorder.dto.StaffCallTypeResponse;
import com.example.tableorder.entity.staffcall.StaffCallType;
import com.example.tableorder.entity.store.Store;
import com.example.tableorder.repository.StaffCallTypeRepository;
import com.example.tableorder.repository.StoreRepository;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StaffCallService {

    private final StoreRepository storeRepository;
    private final StaffCallTypeRepository staffCallTypeRepository;

    // 점주의 직원호출 종류 만들기
    public StaffCallTypeResponse staffCallTypeCreate(Long storeId, StaffCallTypeRequest request) {
        // 1. 매장 정보 가져오기
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 매장이 존재하지 않습니다."));
        // 2. 매장 메세지 등록하기 / 활성화
        StaffCallType staffCallType = StaffCallType.builder()
                .store(store)
                .message(request.getMessage())
                .active(true)
                .build();
        StaffCallType saved = staffCallTypeRepository.save(staffCallType);
        return mapToStaffCallTypeResponse(saved);
    }

    private StaffCallTypeResponse mapToStaffCallTypeResponse(StaffCallType staffCallType) {
        return StaffCallTypeResponse.builder()
                .id(staffCallType.getId())
                .message(staffCallType.getMessage())
                .active(staffCallType.isActive())
                .build();
    }
}
