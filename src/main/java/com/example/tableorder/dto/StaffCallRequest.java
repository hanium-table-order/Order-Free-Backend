package com.example.tableorder.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
class StaffCallRequest {
    private Long tableId;
    private Long callTypeId;
}
