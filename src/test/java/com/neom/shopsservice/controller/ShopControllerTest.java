package com.neom.shopsservice.controller;

import com.neom.shopsservice.ExceptionHandler.CustomException;
import com.neom.shopsservice.controller.ShopController;
import com.neom.shopsservice.model.request.ShopRequest;
import com.neom.shopsservice.model.response.ShopResponse;
import com.neom.shopsservice.service.ShopService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ShopControllerTest {

    @Mock
    private ShopService shopService;

    @InjectMocks
    private ShopController shopController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddShop() throws InterruptedException, ExecutionException {
        // Mocking
        ShopRequest shopRequest = new ShopRequest();
        ShopResponse mockResponse = new ShopResponse(); // You can customize this mock response

        when(shopService.addShop(any())).thenReturn(CompletableFuture.completedFuture(mockResponse));

        // Test
        ResponseEntity<ShopResponse> responseEntity = shopController.addShop(shopRequest);

        // Assertions
        assertThat(responseEntity)
                .isNotNull()
                .extracting(ResponseEntity::getStatusCode)
                .isEqualTo(HttpStatus.CREATED);

        assertThat(responseEntity.getBody()).isNotNull(); // Additional assertions based on your requirements
    }

    @Test
    void testGetNearestShop() throws InterruptedException, ExecutionException {
        // Mocking
        double mockLongitude = 1.0;
        double mockLatitude = 2.0;
        ShopResponse mockResponse = new ShopResponse(); // You can customize this mock response

        when(shopService.getNearestShop(mockLongitude, mockLatitude))
                .thenReturn(CompletableFuture.completedFuture(mockResponse));

        // Test
        ResponseEntity<ShopResponse> responseEntity = shopController.getNearestShop(mockLongitude, mockLatitude);

        // Assertions
        assertThat(responseEntity)
                .isNotNull()
                .extracting(ResponseEntity::getStatusCode)
                .isEqualTo(HttpStatus.OK);

        assertThat(responseEntity.getBody()).isNotNull(); // Additional assertions based on your requirements
    }
}
