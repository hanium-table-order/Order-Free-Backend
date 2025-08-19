package com.example.tableorder.entity.order;

import com.example.tableorder.entity.menu.MenuOption;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "order_item_option")
public class OrderItemOption {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_item_id", nullable = false)
    private OrderItem orderItem;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "option_id", nullable = false)
    private MenuOption option;

    @Column(name = "option_name", nullable = false, length = 255) // 👈 추가됨
    private String optionName;

    @Column(name = "extra_price", nullable = false) // 👈 추가됨
    private Integer extraPrice;
}
