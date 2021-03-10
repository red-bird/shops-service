package com.redbird.shopsservice.controller;

import com.redbird.shopsservice.model.Shop;
import com.redbird.shopsservice.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/shops")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @GetMapping
    public List<Shop> findAll() {
        return shopService.findAll();
    }

    @GetMapping("/{id}")
    public Shop findById(@PathVariable("id") Long id) {
        return shopService.findById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Shop saveGood(@RequestBody Shop shop) {
        return shopService.saveGood(shop);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable("id") Long id) {
        shopService.deleteById(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Shop updateGood(@PathVariable("id") Long id, @RequestBody Map<String, Object> fields) {
        return shopService.updateById(id, fields);
    }
}
