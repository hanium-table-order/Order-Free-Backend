package com.example.tableorder.entity.menu;

import com.example.tableorder.entity.category.Category;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "menu_item")
public class MenuItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "name_ko", nullable = false)
    private String nameKo;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "name_zh")
    private String nameZh;

    @Column(name = "name_ja")
    private String nameJa;

    @Column(nullable = false)
    private Integer price;

    @Lob @Column(name = "description_ko")
    private String descriptionKo;

    @Lob @Column(name = "description_en")
    private String descriptionEn;

    @Lob @Column(name = "description_zh")
    private String descriptionZh;

    @Lob @Column(name = "description_ja")
    private String descriptionJa;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "sold_out", nullable = false)
    private Boolean soldOut;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "enable_inventory", nullable = false)
    private Boolean enableInventory;
}
