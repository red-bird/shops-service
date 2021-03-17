package com.redbird.shopsservice.model;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class BoughtGoodDTO {

    private String name;
    private String description;
    private Double cost;
    private String shopName;
    private String category;
    private Long customerId;
    private Long amount;
    private ZonedDateTime boughtTime;
}
