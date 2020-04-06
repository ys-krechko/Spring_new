package com.it.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Request DTO (Data transfer object) class for HotelRoom entity.
 */
@Getter
@Setter
public class HotelRoomRequestDto {

    private Long id;

    @NotNull(message = "{hotelRoom.type.notNull}")
    @NotEmpty(message = "{hotelRoom.type.notEmpty}")
    @Size(min = 3, max = 50, message = "{hotelRoom.type.size}")
    private String type;

    @NotNull(message = "{hotelRoom.numberOfGuests.notNull}")
    private Integer numberOfGuests;

    @NotNull(message = "{hotelRoom.foodType.notNull}")
    @NotEmpty(message = "{hotelRoom.foodType.notEmpty}")
    @Size(min = 3, max = 50, message = "{hotelRoom.foodType.size}")
    private String foodType;

    @NotNull(message = "{hotelRoom.hotelRoomPrices.notNull}")
    private Set<Long> hotelRoomPriceIds;
}