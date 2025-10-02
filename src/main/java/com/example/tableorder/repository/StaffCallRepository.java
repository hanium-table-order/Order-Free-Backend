package com.example.tableorder.repository;

import com.example.tableorder.entity.staffcall.StaffCall;
import com.example.tableorder.entity.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StaffCallRepository extends JpaRepository<StaffCall,Long> {
    
    // 특정 매장의 모든 직원 호출 조회
    List<StaffCall> findByTable_Store(Store store);
    
    // 특정 테이블의 직원 호출 조회
    List<StaffCall> findByTable_Id(Long tableId);
}
