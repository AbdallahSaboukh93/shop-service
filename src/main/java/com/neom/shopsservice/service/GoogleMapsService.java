package com.neom.shopsservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

import java.util.concurrent.CompletableFuture;

@Service
public class GoogleMapsService {

    @Autowired
    private GeoApiContext geoApiContext;

    @Value("${google.maps.api.key}")
    private String apiKey;


    public CompletableFuture<LatLng> getCoordinates(String address) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                GeocodingResult[] results = GeocodingApi.geocode(geoApiContext, address).await();

                if (results != null && results.length > 0) {
                    return results[0].geometry.location;
                } else {
                    throw new RuntimeException("Unable to get coordinates for the address: " + address);
                }
            } catch (Exception e) {
                throw new RuntimeException("Unexpected error occurred", e);
            }
        });
    }


}
