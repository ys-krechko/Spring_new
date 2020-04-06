package com.it.service;

import com.it.model.HotelsAddress;

import java.util.List;

/**
 * Interface with CRUD methods for HotelsAddress entity
 */
public interface HotelsAddressService {

    /**
     * Finds all HotelsAddress entities
     *
     * @return - List of HotelsAddress entities
     */
    List<HotelsAddress> findAll();

    /**
     * Finds the HotelsAddress entity with the given id
     *
     * @param id - HotelsAddress entity id
     * @return - HotelsAddress entity
     */
    HotelsAddress findById(Long id);

    /**
     * Saves a given HotelsAddress entity
     *
     * @param hotelsAddress - HotelsAddress Entity
     * @return - the saved HotelsAddress entity
     */
    HotelsAddress save(HotelsAddress hotelsAddress);

    /**
     * Updates a HotelsAddress entity and flushes changes instantly
     *
     * @param hotelsAddress - HotelsAddress entity
     * @return - the updated HotelsAddress entity
     */
    HotelsAddress update(HotelsAddress hotelsAddress);

    /**
     * Deletes a given HotelsAddress entity
     *
     * @param hotelsAddress - HotelsAddress entity
     */
    void delete(HotelsAddress hotelsAddress);

    /**
     * Deletes the HotelsAddress entity with the given id
     *
     * @param id - HotelsAddress entity id
     */
    void deleteById(Long id);
}
