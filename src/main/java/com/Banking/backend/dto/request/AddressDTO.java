package com.Banking.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDTO {

    @NotBlank(message = "House number is required")
    private String houseNumber;

    @NotBlank(message = "Street is required")
    private String street;

    private String landmark;

    @NotBlank(message = "Area is required")
    private String area;

    @NotBlank(message = "Pincode is required")
    private String pincode;

    @NotNull(message = "City ID is required")
    private Long cityId;

    @NotBlank(message = "Address type is required")
    private String addressType;  // E.g., HOME, OFFICE
}
