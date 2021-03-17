package com.redbird.shopsservice.model;

import lombok.Data;

import java.util.List;

@Data
public class BuyQuery {
    private Long customerId;
    private String shopName;
    private String paymentMethod;
    private List<GoodDTO> GoodDTOS;
}
