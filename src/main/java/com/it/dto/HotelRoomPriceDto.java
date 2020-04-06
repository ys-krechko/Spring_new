package com.it.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * DTO (Data transfer object) class for HotelRoomPrice entity.
 * Both for response and request
 */
@Getter
@Setter
public class HotelRoomPriceDto {

    private Long id;

    @NotNull(message = "{hotelRoomPrice.date.notNull}")
    @NotEmpty(message = "{hotelRoomPrice.date.notEmpty}")
    private String date;

    @NotNull(message = "{hotelRoomPrice.price.notNull}")
    private Double pricePerNight;
}
