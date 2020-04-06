package com.it.repository;

import com.it.model.HotelsAddress;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA Repository for HotelsAddress entity
 */
public interface HotelsAddressRepository extends JpaRepository<HotelsAddress, Long> {

    /**
     * Checks by HotelsAddress entity's fields if such entity exists
     *
     * @param country        - country
     * @param city           - city
     * @param street         - street
     * @param buildingNumber - building number
     * @return - true if exist, falls if not
     */
    boolean existsByCountryAndCityAndStreetAndBuildingNumber(String country, String city, String street, String buildingNumber);

    /**
     * Find HotelsAddress entity by its fields
     *
     * @param country        - country
     * @param city           - city
     * @param street         - street
     * @param buildingNumber - building number
     * @return - HotelsAddress entity
     */
    HotelsAddress findByCountryAndCityAndStreetAndBuildingNumber(String country, String city, String street, String buildingNumber);
}
