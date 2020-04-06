package com.it.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

/**
 * Table Customer representation
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "customers_first_name", nullable = false)
    @NotNull(message = "{customer.customersFirstName.notNull}")
    @NotEmpty(message = "{customer.customersFirstName.notEmpty}")
    @Size(min = 3, max = 50, message = "{customer.customersFirstName.size}")
    private String customersFirstName;

    @Column(name = "customers_last_name", nullable = false)
    @NotNull(message = "{customer.customersLastName.notNull}")
    @NotEmpty(message = "{customer.customersLastName.notEmpty}")
    @Size(min = 3, max = 50, message = "{customer.customersLastName.size}")
    private String customersLastName;

    @Column(name = "customers_passport_number", unique = true, nullable = false)
    @NotNull(message = "{customer.customersPassportNumber.notNull}")
    @NotEmpty(message = "{customer.customersPassportNumber.notEmpty}")
    @Size(min = 3, max = 50, message = "{customer.customersPassportNumber.size}")
    private String customersPassportNumber;

    @Column(name = "customers_contract_number", unique = true, nullable = false)
    @NotNull(message = "{customer.customersContractNumber.notNull}")
    @NotEmpty(message = "{customer.customersContractNumber.notEmpty}")
    @Size(min = 3, max = 50, message = "{customer.customersContractNumber.size}")
    private String customersContractNumber;

    @Column(name = "customers_contract_date_of_signing", nullable = false)
    @NotNull(message = "{customer.customersContractDateOfSigning.notNull}")
    private LocalDate customersContractDateOfSigning;

    @OneToMany(mappedBy = "customer")
    private Set<OrderList> orderList;

    public Customer(Long id, String customersFirstName, String customersLastName, String customersPassportNumber, String customersContractNumber, LocalDate customersContractDateOfSigning) {
        this.id = id;
        this.customersFirstName = customersFirstName;
        this.customersLastName = customersLastName;
        this.customersPassportNumber = customersPassportNumber;
        this.customersContractNumber = customersContractNumber;
        this.customersContractDateOfSigning = customersContractDateOfSigning;
    }

    public Customer(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", customersFirstName='" + customersFirstName + '\'' +
                ", customersLastName='" + customersLastName + '\'' +
                ", customersPassportNumber='" + customersPassportNumber + '\'' +
                ", customersContractNumber='" + customersContractNumber + '\'' +
                ", customersContractDateOfSigning=" + customersContractDateOfSigning +
                '}';
    }
}
