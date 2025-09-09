package com.example.tableorder.entity.cart;

import com.example.tableorder.entity.menu.MenuOption;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cart_item_option")
public class CartItemOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cart_item_id", nullable = false)
    private CartItem cartItem;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "option_id", nullable = false)
    private MenuOption option;

    @Column(name = "option_name", nullable = false, length = 255)
    private String optionName;

    @Column(name = "extra_price", nullable = false)
    private Integer extraPrice;

    @Column(name = "quantity", nullable = false)
    @NotNull(message = "옵션 수량은 필수입니다")
    private Integer quantity;
}
