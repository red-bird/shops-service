package com.redbird.shopsservice.model;

import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "bought_goods")
public class BoughtGood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Double cost;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shop_id")
    private Shop shop;
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    private Long customerId;
    private Long amount;
    private ZonedDateTime boughtTime;
}
