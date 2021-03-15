package com.redbird.shopsservice.model;

import lombok.Data;

@Data
public class GoodFromFactory {
    private String name;
    private String description;
    private Double cost;
    private Long amount;
    private String shopName;
    private String category;
}
