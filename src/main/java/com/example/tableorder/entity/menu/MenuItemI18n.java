package com.example.tableorder.entity.menu;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(
        name = "menu_item_i18n",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_menu_item_lang",
                columnNames = {"menu_item_id", "lang"}
        )
)
public class MenuItemI18n {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "menu_item_id", nullable = false)
    private MenuItem menuItem;

    // 언어코드: "ko", "en", "zh", "ja" 등 문자열로 관리
    @Column(nullable = false, length = 10)
    private String lang;

    // 번역 대상 필드
    @Column(nullable = false, length = 255)
    private String name;

    @Column(length = 2000)
    private String description;
}
