package com.example.tableorder.entity.order;

import com.example.tableorder.entity.store.StoreTable;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "order")
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "table_id", nullable = false)
    private StoreTable table;

    @Column(nullable = false)
    private String status;

    @Column(name = "total_price", nullable = false)
    private Integer totalPrice;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
