package com.neom.shopsservice.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShopAddressResponse {
    private String streetNumber;
    private String streetName;
    private String postalCode;
    private double longitude;
    private double latitude;
}
