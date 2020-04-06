package com.it.repository;

import com.it.model.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA Repository for Insurance entity
 */
public interface InsuranceRepository extends JpaRepository<Insurance, Long> {

    /**
     * Checks by HotelsAddress entity's insuranceType field if such entity exists
     *
     * @param insuranceType - insuranceType
     * @return - true if exist, falls if not
     */
    boolean existsByInsuranceType(String insuranceType);

    /**
     * Find Insurance entity by insuranceType
     *
     * @param insuranceType - insuranceType
     * @return - Insurance entity
     */
    Insurance findByInsuranceType(String insuranceType);
}
