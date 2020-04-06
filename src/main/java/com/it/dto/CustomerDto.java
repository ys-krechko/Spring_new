package com.it.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO (Data transfer object) class for Customer entity.
 * Both for response and request
 */
@Getter
@Setter
public class CustomerDto {

    private Long id;

    @NotNull(message = "{customer.customersFirstName.notNull}")
    @NotEmpty(message = "{customer.customersFirstName.notEmpty}")
    @Size(min = 3, max = 50, message = "{customer.customersFirstName.size}")
    private String customersFirstName;

    @NotNull(message = "{customer.customersLastName.notNull}")
    @NotEmpty(message = "{customer.customersLastName.notEmpty}")
    @Size(min = 3, max = 50, message = "{customer.customersLastName.size}")
    private String customersLastName;

    @NotNull(message = "{customer.customersPassportNumber.notNull}")
    @NotEmpty(message = "{customer.customersPassportNumber.notEmpty}")
    @Size(min = 3, max = 50, message = "{customer.customersPassportNumber.size}")
    private String customersPassportNumber;

    @NotNull(message = "{customer.customersContractNumber.notNull}")
    @NotEmpty(message = "{customer.customersContractNumber.notEmpty}")
    @Size(min = 3, max = 50, message = "{customer.customersContractNumber.size}")
    private String customersContractNumber;

    @NotNull(message = "{customer.customersContractDateOfSigning.notNull}")
    private String customersContractDateOfSigning;
}
