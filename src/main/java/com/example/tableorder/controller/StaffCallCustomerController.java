package com.example.tableorder.controller;

import com.example.tableorder.dto.StaffCallTypeResponse;
import com.example.tableorder.entity.staffcall.StaffCallType;
import com.example.tableorder.service.StaffCallCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customer/stores/{storeId}")
@RequiredArgsConstructor
public class StaffCallCustomerController {
    private final StaffCallCustomerService staffCallCustomerService;

    @GetMapping("/staff-call-types")
    public ResponseEntity<List<StaffCallTypeResponse>> getStaffCallTypes(@PathVariable("storeId") Long storeId) {
        try{
            List<StaffCallTypeResponse> responses = staffCallCustomerService.getStaffCallTypes(storeId);
            return ResponseEntity.ok(responses);
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }


}
