package com.redbird.shopsservice.repository;

import com.redbird.shopsservice.model.Good;
import com.redbird.shopsservice.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Locale;
import java.util.Set;

public interface GoodRepository extends JpaRepository<Good, Long> {
    List<Good> findByName(String name);
    List<Good> findByCategory(String category);
//    List<Good> findByShop(Shop shop);
}
