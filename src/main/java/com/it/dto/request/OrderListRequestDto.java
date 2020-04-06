package com.it.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Request DTO (Data transfer object) class for OrderList entity.
 */
@Getter
@Setter
public class OrderListRequestDto {

    private Long id;

    @NotNull(message = "{orderList.beginningDateOfTour.notNull}")
    @NotEmpty(message = "{orderList.beginningDateOfTour.notEmpty}")
    private String beginningDateOfTour;

    @NotNull(message = "{orderList.amountOfDaysOfTour.notNull}")
    private Integer amountOfDaysOfTour;

    @NotNull(message = "{orderList.numberOfTourists.notNull}")
    private Integer numberOfTourists;

    @NotNull(message = "{orderList.user.notNull}")
    private Long userId;

    @NotNull(message = "{orderList.insurance.notNull}")
    private Long insuranceId;

    @NotNull(message = "{orderList.customer.notNull}")
    private Long customerId;

    @NotNull(message = "{orderList.hotelRoomHotel.notNull}")
    private Long hotelRoomHotelId;
}