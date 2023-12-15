package com.neom.shopsservice.mapper;

import com.neom.shopsservice.entity.Shop;
import com.neom.shopsservice.entity.ShopAddress;
import com.neom.shopsservice.model.request.ShopAddressRequest;
import com.neom.shopsservice.model.request.ShopRequest;
import com.neom.shopsservice.model.response.ShopAddressResponse;
import com.neom.shopsservice.model.response.ShopResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ShopMapper {

    ShopMapper INSTANCE = Mappers.getMapper(ShopMapper.class);

    @Mapping(source = "shopAddressRequest", target = "shopAddress", qualifiedByName = "mapToShopAddress")
    Shop mapToShop(ShopRequest request);
    @Mapping(source = "shopAddress", target = "shopAddressResponse", qualifiedByName = "mapToShopAddressResponse")
    ShopResponse mapToShopResponse(Shop shop);
    @Named("mapToShopAddressResponse")
    ShopAddressResponse mapToShopAddressResponse(ShopAddress shopAddress);
    @Named("mapToShopAddress")
    ShopAddress mapToShopAddress(ShopAddressRequest shopAddressRequest);

    ShopAddressResponse mapToShopAddressResponseversion(ShopAddress shopAddress);

}