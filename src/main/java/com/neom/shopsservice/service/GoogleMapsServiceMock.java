package com.neom.shopsservice.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.neom.shopsservice.ExceptionHandler.CustomException;
import com.neom.shopsservice.model.response.Coordinates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class GoogleMapsServiceMock {

    @Autowired
    private  RestTemplate restTemplate;
    @Value("${google.maps.api.mock}")
    private  String apiUrl; // URL of the other API


    public CompletableFuture<LatLng> getCoordinates(String address) {
        return CompletableFuture.supplyAsync(() -> {
        try {
            List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
            MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
            converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
            messageConverters.add(converter);
            restTemplate.setMessageConverters(messageConverters);
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<Coordinates> responseEntity = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    entity,
                    Coordinates.class
            );
            Coordinates coordinates = responseEntity.getBody();
            return  new LatLng(coordinates.getLat(),coordinates.getLng() );
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
        });
    }


}
