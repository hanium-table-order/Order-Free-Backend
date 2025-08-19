package com.example.tableorder.entity.menu;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(
        name = "menu_option_i18n",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_menu_option_lang",
                columnNames = {"menu_option_id", "lang"}
        )
)
public class MenuOptionI18n {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "menu_option_id", nullable = false)
    private MenuOption menuOption;

    @Column(nullable = false, length = 10)
    private String lang;   // "ko", "en", "zh", "ja"

    @Column(nullable = false)
    private String name;
}
