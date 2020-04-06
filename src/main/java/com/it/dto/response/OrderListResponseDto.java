package com.it.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * Response DTO (Data transfer object) class for OrderLis entity.
 */
@Getter
@Setter
public class OrderListResponseDto {

    private Long id;

    private String beginningDateOfTour;

    private Integer amountOfDaysOfTour;

    private Integer numberOfTourists;

    private Double totalPrice;

    private Long userId;

    private Long insuranceId;

    private Long customerId;

    private Long hotelRoomHotelId;
}