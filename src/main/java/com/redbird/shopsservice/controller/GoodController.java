package com.redbird.shopsservice.controller;

import com.redbird.shopsservice.model.Category;
import com.redbird.shopsservice.model.Good;
import com.redbird.shopsservice.model.GoodDTO;
import com.redbird.shopsservice.model.Shop;
import com.redbird.shopsservice.service.GoodService;
import com.redbird.shopsservice.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/goods")
public class GoodController {

    @Autowired
    private GoodService goodService;
    @Autowired
    private ShopService shopService;

    @GetMapping
    public List<Good> findAll() {
        return goodService.findAll();
    }

    @GetMapping("/{id}")
    public Good findById(@PathVariable("id") Long id) {
        return goodService.findById(id);
    }

    @PostMapping
    public Good saveGood(@RequestParam String name, @RequestParam String description, @RequestParam Double cost,
                         @RequestParam String shopName, @RequestParam Category category) {
        Shop shop = shopService.findByName(shopName);
        if (shop == null) return null;
        Good good = goodService.findGood(name, description, cost, shopName, category);
        if (good != null) {
            good.setAmount(good.getAmount()+1);
            return goodService.saveGood(good);
        }
        good = new Good();
        good.setCategory(category);
        good.setName(name);
        good.setDescription(description);
        good.setCost(cost);
        good.setShop(shop);
        return goodService.saveGood(good);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        goodService.deleteById(id);
    }

    @PatchMapping("/{id}")
    public Good updateGood(@PathVariable("id") Long id, @RequestBody Map<String, Object> fields) {
        return goodService.updateById(id, fields);
    }
}
