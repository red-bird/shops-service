package com.redbird.shopsservice.model;

import lombok.Data;

@Data
public class GoodDTO {
    private String name;
    private String description;
    private Double cost;
    private String shopName;
    private String category;
}
