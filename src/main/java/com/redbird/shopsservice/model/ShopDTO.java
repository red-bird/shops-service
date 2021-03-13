package com.redbird.shopsservice.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class ShopDTO {
    @Column(unique = true)
    private String name;
    private String address;
    private String number;
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;
}
