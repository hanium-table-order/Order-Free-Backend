package com.example.tableorder.entity.menu;

import com.example.tableorder.entity.category.Category;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "menu_item")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private Integer price;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "sold_out", nullable = false)
    private Boolean soldOut;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "enable_inventory", nullable = false)
    private Boolean enableInventory;

    @Column(name = "prep_time_min", nullable = false)
    private Integer prepTimeMin;

    @OneToMany(mappedBy = "menuItem", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MenuItemI18n> translations = new ArrayList<>();


}
