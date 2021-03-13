package com.redbird.shopsservice.controller;

import com.redbird.shopsservice.model.BoughtGood;
import com.redbird.shopsservice.model.BuyGoodDTO;
import com.redbird.shopsservice.model.GoodDTO;
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
    public List<BoughtGood> findAll() {
        return buyService.findAll();
    }

    @GetMapping("/{id}")
    public BoughtGood findById(@PathVariable("id") Long id) {
        return buyService.findById(id);
    }

    @GetMapping("/name/{name}")
    public List<BoughtGood> findByGoodName(@PathVariable("name") String goodName) {
        return buyService.findByGoodName(goodName);
    }

    @GetMapping("/shop/{shop}")
    public List<BoughtGood> findByShopName(@PathVariable("shop") String shopName) {
        return buyService.findByShopName(shopName);
    }

    @GetMapping("/customer/{customerId}")
    public List<BoughtGood> findByCustomerId(@PathVariable("customerId") Long customerId) {
        return buyService.findByCustomerId(customerId);
    }

    @PostMapping
    public List<BoughtGood> buyGoods(@RequestBody List<BuyGoodDTO> goodDTOList) {
        return buyService.buyGoods(goodDTOList);
    }
}
