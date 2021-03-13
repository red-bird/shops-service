package com.redbird.shopsservice.model;

import lombok.Data;

@Data
public class BuyGoodDTO {
    private Long customerId;
    private GoodDTO goodDTO;
}
