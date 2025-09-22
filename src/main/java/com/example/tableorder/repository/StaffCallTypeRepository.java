package com.example.tableorder.repository;

import com.example.tableorder.entity.staffcall.StaffCallType;
import com.example.tableorder.entity.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StaffCallTypeRepository extends JpaRepository<StaffCallType, Long> {

    List<StaffCallType> findByStore(Store store);
}
