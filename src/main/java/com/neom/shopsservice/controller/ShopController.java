package com.neom.shopsservice.controller;

import com.neom.shopsservice.ExceptionHandler.CustomException;
import com.neom.shopsservice.model.request.ShopRequest;
import com.neom.shopsservice.model.response.ShopResponse;
import com.neom.shopsservice.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/shops")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @PostMapping
    public  ResponseEntity<ShopResponse> addShop(@Validated @RequestBody ShopRequest shopRequest) {
        try {
            ShopResponse result = shopService.addShop(shopRequest).get();
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (InterruptedException | ExecutionException e) {
            // Handle exceptions
            throw  new CustomException("Error processing the request ");
        }
    }

    @GetMapping("/nearest")
    public ResponseEntity<ShopResponse> getNearestShop(
          @NotNull @RequestParam double longitude,
          @NotNull  @RequestParam double latitude) {
        try {
            return new ResponseEntity<>(shopService.getNearestShop(longitude,
                    latitude).get(), HttpStatus.OK);
        } catch (InterruptedException | ExecutionException e) {
            // Handle exceptions
            throw  new CustomException("Error processing the request ");
        }

    }
}
