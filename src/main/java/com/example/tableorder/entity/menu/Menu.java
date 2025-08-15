package com.example.tableorder.entity.menu;

import com.example.tableorder.entity.category.Category;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@Entity
@Table(name = "menu")
@NoArgsConstructor
@AllArgsConstructor
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK: menu.category_id -> category.id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(columnDefinition = "json", nullable = false)
    private String name;             // JSON ({"ko":"김치찌개","en":"Kimchi Stew"})

    @Column(nullable = false)
    private int price;

    @Column(columnDefinition = "json", nullable = false)
    private String description;      // JSON

    @Column(columnDefinition = "json")
    private String options;          // JSON 배열(옵션 목록), nullable

    private String image;            // URL

    @Column(nullable = false)
    private boolean soldOut;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private boolean enableInventory;
}
