package com.it.service;

import com.it.model.Customer;

import java.util.List;

/**
 * Interface with CRUD methods for Customer entity
 */
public interface CustomerService {

    /**
     * Finds all Customer entities
     *
     * @return - List of Customer entities
     */
    List<Customer> findAll();

    /**
     * Finds the Customer entity with the given id
     *
     * @param id - Customer entity id
     * @return - Customer entity
     */
    Customer findById(Long id);

    /**
     * Saves a given Customer entity
     *
     * @param customer - Customer Entity
     * @return - the saved Customer entity
     */
    Customer save(Customer customer);

    /**
     * Updates a Customer entity and flushes changes instantly
     *
     * @param customer - Customer entity
     * @return - the updated Customer entity
     */
    Customer update(Customer customer);

    /**
     * Deletes a given Customer entity
     *
     * @param customer - Hotel entity
     */
    void delete(Customer customer);

    /**
     * Deletes the Customer entity with the given id
     *
     * @param id - Customer entity id
     */
    void deleteById(Long id);
}
