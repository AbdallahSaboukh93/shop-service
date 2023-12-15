package com.neom.shopsservice.model.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ShopResponse {

    private String shopName;
    private ShopAddressResponse shopAddressResponse;
    private List<ShopAddressResponse> shopAddressVersions;
}
