package com.example.tableorder.entity.category;

import com.example.tableorder.entity.menu.MenuItem;
import com.example.tableorder.entity.store.Store;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                               // bigint PK

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;                           // FK -> store.id

    @Column(name = "name_ko", nullable = false)
    private String nameKo;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "name_zh")
    private String nameZh;

    @Column(name = "name_ja")
    private String nameJa;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MenuItem> menuItems = new ArrayList<>();  // MenuItem과의 양방향 매핑 추가
}