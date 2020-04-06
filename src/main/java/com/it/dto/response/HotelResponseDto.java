package com.it.dto.response;

import com.it.dto.HotelsAddressDto;
import lombok.Getter;
import lombok.Setter;

/**
 * Response DTO (Data transfer object) class for Hotel entity.
 */
@Getter
@Setter
public class HotelResponseDto {

    private Long id;

    private String hotelsName;

    private Integer stars;

    private HotelsAddressDto hotelsAddress;
}
