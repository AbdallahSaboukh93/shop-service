package com.neom.shopsservice.service;

import com.google.maps.model.LatLng;
import com.neom.shopsservice.ExceptionHandler.CustomException;
import com.neom.shopsservice.entity.Shop;
import com.neom.shopsservice.mapper.ShopMapper;
import com.neom.shopsservice.model.request.ShopRequest;
import com.neom.shopsservice.model.response.ShopResponse;
import com.neom.shopsservice.repositories.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@Service
public class ShopService {

    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private GoogleMapsService googleMapsService;

    @Autowired
    private GoogleMapsServiceMock  googleMapsServiceMock;

    public CompletableFuture<ShopResponse> addShop(ShopRequest shopRequest) {
        return CompletableFuture.supplyAsync(() -> {
            Shop shopToAdd = ShopMapper.INSTANCE.mapToShop(shopRequest);
            CompletableFuture<Void> future = getLocation(shopRequest, shopToAdd);
            future.join();
            Shop savedShop = shopRepository.save(shopToAdd);
            return ShopMapper.INSTANCE.mapToShopResponse(savedShop);
        });
    }
    public CompletableFuture<Void> getLocation(ShopRequest shopRequest, Shop shopToAdd) {
        return CompletableFuture.runAsync(() -> {
            try {
                CompletableFuture<LatLng>  coordinatesFuture = googleMapsServiceMock.getCoordinates(shopRequest.getShopAddressRequest().getFullAddress());
                coordinatesFuture.join();
                LatLng coordinates = coordinatesFuture.get();
                shopToAdd.getShopAddress().setLongitude(coordinates.lng);
                shopToAdd.getShopAddress().setLatitude(coordinates.lat);
            } catch (Exception e) {
                throw new CustomException(e.getMessage());
            }
        });
    }

    public CompletableFuture<ShopResponse> getNearestShop(double customerLongitude, double customerLatitude) {
        return CompletableFuture.supplyAsync(() -> {
            List<Shop> shopList = shopRepository.findNearestShop(customerLongitude, customerLatitude);

            if (shopList != null && !shopList.isEmpty()) {
                Optional<Shop> nearestShop = shopList.stream().findFirst();
                return ShopMapper.INSTANCE.mapToShopResponse(nearestShop.get());
            }

            throw new CustomException("No Shops");
        });
    }





}
