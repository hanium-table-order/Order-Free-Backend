package com.example.tableorder.repository;

import com.example.tableorder.entity.staffcall.StaffCall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffCallRepository extends JpaRepository<StaffCall,Long> {
}
