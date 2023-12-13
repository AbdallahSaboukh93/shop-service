package com.neom.shopsservice.model.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShopResponse {

    private String shopName;
    private ShopAddressResponse shopAddressResponse;
}
