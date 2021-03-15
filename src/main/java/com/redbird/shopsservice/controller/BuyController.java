package com.redbird.shopsservice.controller;

import com.redbird.shopsservice.model.*;
import com.redbird.shopsservice.service.BuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/buy")
public class BuyController {

    @Autowired
    private BuyService buyService;

    @GetMapping
    public List<BoughtGoodDTO> findAll() {
        return buyService.findAll();
    }

    @GetMapping("/{id}")
    public BoughtGoodDTO findById(@PathVariable("id") Long id) {
        return buyService.findById(id);
    }

    @GetMapping("/name/{name}")
    public List<BoughtGoodDTO> findByGoodName(@PathVariable("name") String goodName) {
        return buyService.findByGoodName(goodName);
    }

    @GetMapping("/shop/{shop}")
    public List<BoughtGoodDTO> findByShopName(@PathVariable("shop") String shopName) {
        return buyService.findByShopName(shopName);
    }

    @GetMapping("/customer/{customerId}")
    public List<BoughtGoodDTO> findByCustomerId(@PathVariable("customerId") Long customerId) {
        return buyService.findByCustomerId(customerId);
    }

    @PostMapping
    public List<BoughtGoodDTO> buyGoods(@RequestBody BuyQuery buyQuery) {
            return buyService.buyGoods(buyQuery);
    }
}
