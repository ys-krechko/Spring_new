package com.it.repository;

import com.it.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA Repository for Customer entity
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Checks by Customer entity's customersPassportNumber field if such entity exists
     *
     * @param customersPassportNumber - customersPassportNumber
     * @return - true if exist, falls if not
     */
    boolean existsByCustomersPassportNumber(String customersPassportNumber);

    /**
     * Checks by Customer entity's customersContractNumber field if such entity exists
     *
     * @param customersContractNumber - customersContractNumber
     * @return - true if exist, falls if not
     */
    boolean existsByCustomersContractNumber(String customersContractNumber);

    /**
     * Find Customer entity by customersPassportNumber
     *
     * @param customersPassportNumber - customersPassportNumber
     * @return - Customer entity
     */
    Customer findByCustomersPassportNumber(String customersPassportNumber);

    /**
     * Find Customer entity by customersContractNumber
     *
     * @param customersContractNumber - customersContractNumber
     * @return - Customer entity
     */
    Customer findByCustomersContractNumber(String customersContractNumber);
}
