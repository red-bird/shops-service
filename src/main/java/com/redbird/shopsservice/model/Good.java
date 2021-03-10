package com.redbird.shopsservice.model;

import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "shops")
public class Good {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Double cost;
    private Long amount = 0L;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shop_id")
    private Shop shop;
//    @ElementCollection(targetClass = Category.class, fetch = FetchType.EAGER)
//    @CollectionTable(name = "good_category", joinColumns = @JoinColumn(name = "good_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;
//    @ElementCollection(targetClass = Locale.Category.class, fetch = FetchType.EAGER)
//    @CollectionTable(name = "good_status", joinColumns = @JoinColumn(name = "good_id"))
//    @Enumerated(EnumType.STRING)
//    private Status status;
    private ZonedDateTime createdTime = ZonedDateTime.now();
    private ZonedDateTime updatedTime = createdTime;
}
