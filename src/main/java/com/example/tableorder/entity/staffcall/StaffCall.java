package com.example.tableorder.entity.staffcall;

import com.example.tableorder.entity.store.StoreTable;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "staff_call")
public class StaffCall {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "table_id", nullable = false)
    private StoreTable table;

    @Lob
    private String message;

    @Column(name = "requested_at")
    private LocalDateTime requestedAt;
}
