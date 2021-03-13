package com.redbird.shopsservice.controller;

import com.redbird.shopsservice.model.*;
import com.redbird.shopsservice.service.GoodService;
import com.redbird.shopsservice.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/goods")
public class GoodController {

    public <E extends Enum<E>> boolean isInEnum(String value, Class<E> enumClass) {
        for (E e : enumClass.getEnumConstants()) {
            if(e.name().equals(value)) { return true; }
        }
        return false;
    }

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

    @GetMapping("/category/{category}")
    public List<Good> findByCategory(@PathVariable("category") String category) {
        return isInEnum(category, Category.class) ?  goodService.findByCategory(Category.valueOf(category)) : null;
    }

    @GetMapping("/name/{name}")
    public List<Good> findByName(@PathVariable("name") String name) {
        return goodService.findByName(name);
    }

    @GetMapping("/shop/{shop}")
    public List<Good> findByShop(@PathVariable("shop") String shopName) {
        Shop shop = shopService.findByName(shopName);
        if (shop == null) return null;
        return goodService.findByShop(shop);
    }

    @PostMapping
    public Good saveGood(@RequestBody GoodDTO goodDTO) {
        // check if category doesn't exists
        if (!isInEnum(goodDTO.getCategory(), Category.class)) {
            return null;
        }
        // check if shop exists
        Shop shop = shopService.findByName(goodDTO.getShopName());
        if (shop == null) return null;
        // check if good already exists
        Good good = goodService.findGood(goodDTO.getName(), goodDTO.getDescription(), goodDTO.getCost(),
                goodDTO.getShopName(), Category.valueOf(goodDTO.getCategory()));
        if (good != null) {
            good.setAmount(good.getAmount()+goodDTO.getAmount());
            return goodService.saveGood(good);
        }
        // create new good
        good = new Good();
        good.setCategory(Category.valueOf(goodDTO.getCategory()));
        good.setName(goodDTO.getName());
        good.setDescription(goodDTO.getDescription());
        good.setCost(goodDTO.getCost());
        good.setShop(shop);
        good.setAmount(goodDTO.getAmount());
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
