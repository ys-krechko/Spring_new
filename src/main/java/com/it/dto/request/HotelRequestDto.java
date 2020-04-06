package com.it.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Request DTO (Data transfer object) class for Hotel entity.
 */
@Getter
@Setter
public class HotelRequestDto {

    private Long id;

    @NotNull(message = "{hotel.name.notNull}")
    @NotEmpty(message = "{hotel.name.notEmpty}")
    @Size(min = 3, max = 50, message = "{hotel.name.size}")
    private String hotelsName;

    @NotNull(message = "{hotel.stars.notNull}")
    private Integer stars;

    @NotNull(message = "{hotel.hotelsAddress.notNull}")
    private Long hotelsAddressId;
}
