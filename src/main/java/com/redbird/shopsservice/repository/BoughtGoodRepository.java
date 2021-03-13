package com.redbird.shopsservice.repository;

import com.redbird.shopsservice.model.BoughtGood;
import com.redbird.shopsservice.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoughtGoodRepository extends JpaRepository<BoughtGood, Long> {
    List<BoughtGood> findByCustomerId(Long id);
    List<BoughtGood> findByShop(Shop shop);
    List<BoughtGood> findByName(String name);
}
