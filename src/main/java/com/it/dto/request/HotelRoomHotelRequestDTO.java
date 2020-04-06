package com.it.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Request DTO (Data transfer object) class for HotelRoomHotel entity.
 */
@Getter
@Setter
public class HotelRoomHotelRequestDTO {

    private Long id;

    @NotNull(message = "{hotelRoomHotel.hotel.notNull}")
    private Long hotelId;

    @NotNull(message = "{hotelRoomHotel.hotelRoom.notNull}")
    private Long hotelRoomId;
}
