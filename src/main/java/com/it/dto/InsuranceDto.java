package com.it.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO (Data transfer object) class for Insurance entity.
 * Both for response and request
 */
@Getter
@Setter
public class InsuranceDto {

    private Long id;

    @NotNull(message = "{insurance.type.notNull}")
    @NotEmpty(message = "{insurance.type.notEmpty}")
    @Size(min = 3, max = 50, message = "{insurance.type.size}")
    private String insuranceType;

    @NotNull(message = "{insurance.price.notNull}")
    private Double insurancePrice;
}
