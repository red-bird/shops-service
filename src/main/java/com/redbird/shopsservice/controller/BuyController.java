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
    BuyService buyService;

    @GetMapping
    public List<BoughtGood> findAll() {
        return buyService.findAll();
    }

    @GetMapping("/name/{goodName}")
    public List<BoughtGood> findByGoodName(@RequestParam("goodName") String goodName) {
        return buyService.findByGoodName(goodName);
    }

    @GetMapping("/shop/{shopName}")
    public List<BoughtGood> findByShopName(@RequestParam("shopName") String shopName) {
        return buyService.findByShopName(shopName);
    }

    @GetMapping("/customer/{customerId}")
    public List<BoughtGood> findByCustomerId(@RequestParam("customerId") Long customerId) {
        return buyService.findByCustomerId(customerId);
    }

    @PostMapping("/buyGood")
    public List<BoughtGood> buyGoods(@RequestBody List<BuyGoodDTO> goodDTOList) {
        return buyService.buyGoods(goodDTOList);
    }
}
