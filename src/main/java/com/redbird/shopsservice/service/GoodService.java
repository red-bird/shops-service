package com.redbird.shopsservice.service;

import com.redbird.shopsservice.model.Category;
import com.redbird.shopsservice.model.Good;
import com.redbird.shopsservice.model.GoodDTO;
import com.redbird.shopsservice.model.Shop;
import com.redbird.shopsservice.repository.GoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@Service
public class GoodService {

    @Autowired
    private GoodRepository goodRepository;

    @Autowired
    private ShopService shopService;

    public List<Good> findAll() {
        return goodRepository.findAll();
    }

    public Good findById(Long id) {
        return goodRepository.findById(id).orElse(null);
    }

    public List<Good> findByName(String name) {
        return goodRepository.findByName(name);
    }

    public Good findGood(String name, String description, String shopName, Category category) {
        Shop shop = shopService.findByName(shopName);
        return goodRepository.findByNameAndDescriptionAndShopAndCategory(name, description, shop, category);
    }

    public Good findGoodWithoutCategory(String name, String description, Double cost, String shopName){
        Shop shop = shopService.findByName(shopName);
        return goodRepository.findByNameAndDescriptionAndCostAndShop(name, description, cost, shop);
    }

    public List<Good> findByCategory(Category category) {
        return goodRepository.findByCategory(category);
    }

    public List<Good> findByShop(Shop shop) {
        return goodRepository.findByShop(shop);
    }

    public synchronized Good saveGood(Good good) {
        return goodRepository.save(good);
    }

    public void deleteById(Long id) {
        goodRepository.deleteById(id);
    }

    public synchronized Good updateById(Long id, Map<String, Object> fields) {
        Good good = findById(id);
        if (good == null) {
            return null;
        }

        fields.forEach((name, value) -> {
            try{
                if (name.equals("name")) {
                    good.setName((String) value);
                }
                if (name.equals("description")) {
                    good.setDescription((String) value);
                }
                if (name.equals("cost")) {
                    good.setCost(Double.parseDouble((String)value));
                }
                if (name.equals("amount")) {
                    good.setAmount(Long.parseLong((String)value));
                }
                if (name.equals("category")) {
                    good.setCategory(Category.valueOf((String)value));
                }
//                if (name.equals("status")) {
//                    good.setStatus(Status.valueOf((String)value));
//                }
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        good.setUpdatedTime(ZonedDateTime.now());
        return saveGood(good);
    }
}
