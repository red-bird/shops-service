package com.redbird.shopsservice.repository;

import com.redbird.shopsservice.model.Category;
import com.redbird.shopsservice.model.Good;
import com.redbird.shopsservice.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;
import java.util.Set;

@Repository
public interface GoodRepository extends JpaRepository<Good, Long> {
    List<Good> findByName(String name);
    List<Good> findByCategory(Category category);
    Good findByNameAndDescriptionAndCostAndShopNameAndCategory(String name, String description, Double cost, String shopName, Category category);
    List<Good> findByShop(Shop shop);
}
