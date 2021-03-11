package com.redbird.shopsservice.repository;

import com.redbird.shopsservice.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    Shop findByName(String name);
}
