package com.redbird.shopsservice.service;

import com.redbird.shopsservice.model.Category;
import com.redbird.shopsservice.model.Shop;
import com.redbird.shopsservice.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ShopService {

    @Autowired
    private ShopRepository shopRepository;

    public List<Shop> findAll() {
        return shopRepository.findAll();
    }

    public Shop findById(Long id) {
        return shopRepository.findById(id).orElse(null);
    }

    public Shop findByName(String name) {
        return shopRepository.findByName(name);
    }

    public List<Shop> findByCategory(Category category) {
        return shopRepository.findByCategory(category);
    }

    public Shop saveShop(Shop shop) {
        return shopRepository.save(shop);
    }

    public void deleteById(Long id) {
        shopRepository.deleteById(id);
    }

    public Shop updateById(Long id, Map<String, Object> fields) {
        Shop shop = findById(id);
        if (shop == null) {
            return null;
        }

        fields.forEach((name, value) -> {
            try{
                if (name.equals("name")) {
                    shop.setName((String) value);
                }
                if (name.equals("address")) {
                    shop.setAddress((String) value);
                }
                if (name.equals("number")) {
                    shop.setNumber((String) value);
                }
                if (name.equals("category")) {
                    shop.setCategory(Category.valueOf((String) value));
                }
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        shop.setUpdatedTime(ZonedDateTime.now());
        return shopRepository.save(shop);
    }
}
