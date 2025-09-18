package com.example.tableorder.entity.staffcall;

import com.example.tableorder.entity.store.StoreTable;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "staff_call")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class StaffCall {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "table_id", nullable = false)
    private StoreTable table;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "call_type_id", nullable = false)
    private StaffCallType callType; // 어떤 호출 종류를 눌렀는지

    @Column(name = "requested_at", nullable = false)
    private LocalDateTime requestedAt;
}
