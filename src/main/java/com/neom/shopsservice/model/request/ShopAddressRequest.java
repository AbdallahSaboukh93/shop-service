package com.neom.shopsservice.model.request;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ShopAddressRequest {

    @NotBlank(message = "Street Number must not be blank")
    private String streetNumber;
    @NotBlank(message = "Street Name must not be blank")
    private String streetName;
    @NotBlank(message = "Postal Code must not be blank")
    private String postalCode;
    public String getFullAddress() {
        return streetNumber + " " + streetName + ", " + postalCode;
    }

}
