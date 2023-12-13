package com.neom.shopsservice.repositories;

import com.neom.shopsservice.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, String> {


    @Query("SELECT s FROM Shop s ORDER BY SQRT(POWER(s.shopAddress.latitude - :targetLatitude, 2) + POWER(s.shopAddress.longitude - :targetLongitude, 2))")
    List<Shop> findNearestShop(@Param("targetLatitude") double targetLatitude, @Param("targetLongitude") double targetLongitude);

}
