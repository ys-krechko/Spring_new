package com.it.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO (Data transfer object) class for HotelsAddress entity.
 * Both for response and request
 */
@Getter
@Setter
public class HotelsAddressDto {

    private Long id;

    @NotNull(message = "{hotelsAddress.postCode.notNull}")
    @NotEmpty(message = "{hotelsAddress.postCode.notEmpty}")
    @Size(min = 3, max = 50, message = "{hotelsAddress.postCode.size}")
    private String postCode;

    @NotNull(message = "{hotelsAddress.country.notNull}")
    @NotEmpty(message = "{hotelsAddress.country.notEmpty}")
    @Size(min = 3, max = 50, message = "{hotelsAddress.country.size}")
    private String country;

    @NotNull(message = "{hotelsAddress.city.notNull}")
    @NotEmpty(message = "{hotelsAddress.city.notEmpty}")
    @Size(min = 3, max = 50, message = "{hotelsAddress.city.size}")
    private String city;

    @NotNull(message = "{hotelsAddress.street.notNull}")
    @NotEmpty(message = "{hotelsAddress.street.notEmpty}")
    @Size(min = 3, max = 50, message = "{hotelsAddress.street.size}")
    private String street;

    @NotNull(message = "{hotelsAddress.buildingNumber.notNull}")
    @NotEmpty(message = "{hotelsAddress.buildingNumber.notEmpty}")
    @Size(min = 1, max = 10, message = "{hotelsAddress.buildingNumber.size}")
    private String buildingNumber;
}
