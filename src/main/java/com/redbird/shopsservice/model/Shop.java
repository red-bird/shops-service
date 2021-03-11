package com.redbird.shopsservice.model;

import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "shops")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String address;
    private String number;
//    private List<Good> goods;
//    @ElementCollection(targetClass = Category.class, fetch = FetchType.EAGER)
//    @CollectionTable(name = "shop_category", joinColumns = @JoinColumn(name = "shop_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;
//    @ElementCollection(targetClass = Category.class, fetch = FetchType.EAGER)
//    @CollectionTable(name = "shop_status", joinColumns = @JoinColumn(name = "shop_id"))
//    @Enumerated(EnumType.STRING)
//    private Status status;
    private ZonedDateTime createdTime = ZonedDateTime.now();
    private ZonedDateTime updatedTime = createdTime;
}
