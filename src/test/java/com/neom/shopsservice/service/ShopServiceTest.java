package com.neom.shopsservice.service;

import com.google.maps.model.LatLng;
import com.neom.shopsservice.entity.Shop;
import com.neom.shopsservice.mapper.ShopMapper;
import com.neom.shopsservice.model.request.ShopAddressRequest;
import com.neom.shopsservice.model.request.ShopRequest;
import com.neom.shopsservice.model.response.ShopResponse;
import com.neom.shopsservice.repositories.ShopRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ShopServiceTest {

    @Mock
    private ShopRepository shopRepository;

    @Mock
    private GoogleMapsServiceMock googleMapsServiceMock;

    @InjectMocks
    private ShopService shopService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testAddShop() {
        // Mocking
        ShopRequest shopRequest = getShopRequest();

        Shop shopToAdd = ShopMapper.INSTANCE.mapToShop(shopRequest);

        CompletableFuture<LatLng> coordinatesFuture = CompletableFuture.completedFuture(new LatLng(37.7749, -122.4194));
        Mockito.when(googleMapsServiceMock.getCoordinates(any())).thenReturn(coordinatesFuture);

        Mockito.when(shopRepository.save(any(Shop.class))).thenReturn(shopToAdd);

        // Test
        CompletableFuture<ShopResponse> resultFuture = shopService.addShop(shopRequest);

        try {
            ShopResponse result = resultFuture.get();
            assertNotNull(result);
            // Add additional assertions based on your requirements
        } catch (InterruptedException | ExecutionException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }




    @Test
    public void testGetNearestShop() {
        // Mocking
        double customerLongitude = 37.7749;
        double customerLatitude = -122.4194;

        Shop shop = new Shop(); // Create a shop based on your requirements
        Mockito.when(shopRepository.findNearestShop(customerLongitude, customerLatitude)).thenReturn(Collections.singletonList(shop));

        // Test
        CompletableFuture<ShopResponse> resultFuture = shopService.getNearestShop(customerLongitude, customerLatitude);

        try {
            ShopResponse result = resultFuture.get();
            assertNotNull(result);
            // Add additional assertions based on your requirements
        } catch (InterruptedException | ExecutionException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    private static ShopRequest getShopRequest() {
        ShopRequest shopRequest = new ShopRequest();
        shopRequest.setShopName("shop1");
        ShopAddressRequest shopAddressRequest = new ShopAddressRequest();
        shopAddressRequest.setPostalCode("123");
        shopAddressRequest.setStreetName("aa");
        shopAddressRequest.setStreetNumber("1234");
        shopRequest.setShopAddressRequest(shopAddressRequest);
        return shopRequest;
    }
}
