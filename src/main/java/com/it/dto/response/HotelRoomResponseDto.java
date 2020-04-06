package com.it.dto.response;

import com.it.dto.HotelRoomPriceDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * Response DTO (Data transfer object) class for HotelRoom entity.
 */
@Getter
@Setter
public class HotelRoomResponseDto {

    private Long id;

    private String type;

    private Integer numberOfGuests;

    private String foodType;

    private Set<HotelRoomPriceDto> hotelRoomPrices;
}