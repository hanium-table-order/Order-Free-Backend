package com.example.tableorder.service;


import com.example.tableorder.dto.StaffCallTypeRequest;
import com.example.tableorder.dto.StaffCallTypeResponse;
import com.example.tableorder.entity.staffcall.StaffCallType;
import com.example.tableorder.entity.store.Store;
import com.example.tableorder.repository.StaffCallTypeRepository;
import com.example.tableorder.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffCallCustomerService {
    private final StoreRepository storeRepository;
    private final StaffCallTypeRepository staffCallTypeRepository;

    private StaffCallTypeResponse mapToStaffCallTypeResponse(StaffCallType staffCallType) {
        return StaffCallTypeResponse.builder()
                .id(staffCallType.getId())
                .message(staffCallType.getMessage())
                .active(staffCallType.isActive())
                .build();

    }

    // 손님이 직원호출 종류 조회
    public List<StaffCallTypeResponse> getStaffCallTypes(Long storeId){
        // 1. 매장 존재여부 확인
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 매장이 존재하지 않습니다."));

        // 2. 해당 매장의 모든 직원호출 타입 조회 (점주와 다르게 활성화된 것만 가져옴)
        List<StaffCallType> staffCallTypes = staffCallTypeRepository.findByStoreAndActiveTrue(store);

        // 3. 응답 DTO로 변환하여 응답
        return staffCallTypes.stream()
                .map(this::mapToStaffCallTypeResponse)
                .toList();
    }
}
