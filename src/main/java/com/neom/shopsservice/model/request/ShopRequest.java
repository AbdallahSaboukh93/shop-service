package com.neom.shopsservice.model.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class ShopRequest {
    @NotBlank(message = "Street Number must not be blank")
    private String shopName;

    private  ShopAddressRequest shopAddressRequest;
}
