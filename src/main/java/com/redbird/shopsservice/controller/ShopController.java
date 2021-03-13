package com.redbird.shopsservice.controller;

import com.redbird.shopsservice.model.Category;
import com.redbird.shopsservice.model.Shop;
import com.redbird.shopsservice.model.ShopDTO;
import com.redbird.shopsservice.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/shops")
public class ShopController {

    public <E extends Enum<E>> boolean isInEnum(String value, Class<E> enumClass) {
        for (E e : enumClass.getEnumConstants()) {
            if(e.name().equals(value)) { return true; }
        }
        return false;
    }

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

    @GetMapping("/name/{name}")
    public Shop findByName(@PathVariable("name") String name) {
        return shopService.findByName(name);
    }

    @GetMapping("/category/{category}")
    public List<Shop> findByCategory(@PathVariable("category") String category) {
        return isInEnum(category, Category.class) ?  shopService.findByCategory(Category.valueOf(category)) : null;
    }

    @PostMapping
    public Shop saveShop(@RequestBody ShopDTO shopDTO) {
        Shop res = shopService.findByName(shopDTO.getName());
        if (res != null) return null;
        Shop shop = new Shop();
        shop.setCategory(shopDTO.getCategory());
        shop.setName(shopDTO.getName());
        shop.setAddress(shopDTO.getAddress());
        shop.setNumber(shopDTO.getNumber());
        return shopService.saveShop(shop);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        shopService.deleteById(id);
    }

    @PatchMapping("/{id}")
    public Shop updateShop(@PathVariable("id") Long id, @RequestBody Map<String, Object> fields) {
        return shopService.updateById(id, fields);
    }
}
