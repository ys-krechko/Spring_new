package com.it.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * Response DTO (Data transfer object) class for HotelRoomHotel entity.
 */
@Getter
@Setter
public class HotelRoomHotelResponseDTO {

    private Long id;

    private HotelResponseDto hotel;

    private HotelRoomResponseDto hotelRoom;
}
