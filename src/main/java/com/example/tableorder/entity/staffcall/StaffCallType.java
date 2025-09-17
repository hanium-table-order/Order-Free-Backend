package com.example.tableorder.entity.staffcall;

import com.example.tableorder.entity.store.Store;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "staff_call_type")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class StaffCallType {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;  // 어느 매장의 호출 종류인지

    @Column(nullable = false)
    private String message;  // 예: "물 주세요", "계산", "자리 정리"

    @Builder.Default
    private boolean active = true; // 점주가 비활성화할 수도 있게
}
