package com.neom.shopsservice.service;

import com.google.maps.model.LatLng;
import com.neom.shopsservice.model.response.Coordinates;
import com.neom.shopsservice.service.GoogleMapsServiceMock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.*;

@SpringBootTest
public class GoogleMapsServiceMockTest {

    @Autowired
    private GoogleMapsServiceMock googleMapsServiceMock;

    @MockBean
    private RestTemplate restTemplate; // Mocked RestTemplate bean

    @Test
    public void testGetCoordinates() {
        Coordinates coordinates =   new Coordinates();
        coordinates.setLat(10.0);
        coordinates.setLng(20.0);
        // Mock the behavior of RestTemplate
        when(restTemplate.exchange(anyString(), any(), any(), eq(Coordinates.class)))
                .thenReturn(new ResponseEntity<>(coordinates, HttpStatus.OK));

        // Call the method being tested
        CompletableFuture<LatLng> result = googleMapsServiceMock.getCoordinates("someAddress");

        // Assert or verify the expected behavior
        // For example, if you're using JUnit Jupiter and AssertJ:
        Assertions.assertThat(result.join()).isEqualTo(new LatLng(10.0, 20.0));

        // Verify that the restTemplate.exchange method was called with the expected arguments
        verify(restTemplate, times(1)).exchange(anyString(), any(), any(), eq(Coordinates.class));
    }
}
