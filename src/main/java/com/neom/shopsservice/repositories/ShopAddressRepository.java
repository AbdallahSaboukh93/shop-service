package com.neom.shopsservice.repositories;

import com.neom.shopsservice.entity.ShopAddress;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface ShopAddressRepository extends JpaRepository<ShopAddress, Long> {
    List<ShopAddress> findByShopShopName(String shopName);

}
