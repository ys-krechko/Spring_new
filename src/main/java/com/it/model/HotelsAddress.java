package com.it.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Table HotelsAddress representation
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Hotels_Address")
public class HotelsAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "post_code", nullable = false)
    @NotNull(message = "{hotelsAddress.postCode.notNull}")
    @NotEmpty(message = "{hotelsAddress.postCode.notEmpty}")
    @Size(min = 3, max = 50, message = "{hotelsAddress.postCode.size}")
    private String postCode;

    @Column(nullable = false)
    @NotNull(message = "{hotelsAddress.country.notNull}")
    @NotEmpty(message = "{hotelsAddress.country.notEmpty}")
    @Size(min = 3, max = 50, message = "{hotelsAddress.country.size}")
    private String country;

    @Column(nullable = false)
    @NotNull(message = "{hotelsAddress.city.notNull}")
    @NotEmpty(message = "{hotelsAddress.city.notEmpty}")
    @Size(min = 3, max = 50, message = "{hotelsAddress.city.size}")
    private String city;

    @Column(nullable = false)
    @NotNull(message = "{hotelsAddress.street.notNull}")
    @NotEmpty(message = "{hotelsAddress.street.notEmpty}")
    @Size(min = 3, max = 50, message = "{hotelsAddress.street.size}")
    private String street;

    @Column(name = "building_number", nullable = false)
    @NotNull(message = "{hotelsAddress.buildingNumber.notNull}")
    @NotEmpty(message = "{hotelsAddress.buildingNumber.notEmpty}")
    @Size(min = 1, max = 10, message = "{hotelsAddress.buildingNumber.size}")
    private String buildingNumber;

    @OneToOne(mappedBy = "hotelsAddress")
    private Hotel hotel;

    public HotelsAddress(Long id, String postCode, String country, String city, String street, String buildingNumber) {
        this.id = id;
        this.postCode = postCode;
        this.country = country;
        this.city = city;
        this.street = street;
        this.buildingNumber = buildingNumber;
    }

    public HotelsAddress(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "HotelsAddress{" +
                "id=" + id +
                ", postCode='" + postCode + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", buildingNumber='" + buildingNumber + '\'' +
                '}';
    }
}
