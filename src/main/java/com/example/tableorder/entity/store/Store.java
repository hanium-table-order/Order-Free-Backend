package com.example.tableorder.entity.store;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "store")
public class Store {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "business_number")
    private String businessNumber;

    private String name;
    private String address;
    private String hours;

    @Column(name = "floorplan_url")
    private String floorplanUrl;
}

