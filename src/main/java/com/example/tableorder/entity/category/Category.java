package com.example.tableorder.entity.category;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@Entity
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @Column(length = 64)
    private String id;                       // VARCHAR(64)

    @Column(columnDefinition = "json", nullable = false)
    private String name;                     // JSON: {"ko":"한식","en":"Korean"}
}
