package com.example.tableorder.entity.payment;

import com.example.tableorder.entity.order.Order;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "payment")
public class Payment {

    @Id
    @Column(name = "transaction_id")
    private String transactionId;             // varchar PK

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private Integer status;                   // int
    private Integer amount;                   // int
    private String method;                    // varchar

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;
}
