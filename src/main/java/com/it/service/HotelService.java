package com.it.service;

import com.it.model.Hotel;

import java.util.List;

/**
 * Interface with CRUD methods for Hotel entity
 */
public interface HotelService {

    /**
     * Finds all Hotel entities
     *
     * @return - List of Hotel entities
     */
    List<Hotel> findAll();

    /**
     * Finds the Hotel entity with the given id
     *
     * @param id - Hotel entity id
     * @return - Hotel entity
     */
    Hotel findById(Long id);

    /**
     * Saves a given Hotel entity
     *
     * @param hotel - Hotel Entity
     * @return - the saved Hotel entity
     */
    Hotel save(Hotel hotel);

    /**
     * Updates a Hotel entity and flushes changes instantly
     *
     * @param hotel - Hotel entity
     * @return - the updated Hotel entity
     */
    Hotel update(Hotel hotel);

    /**
     * Deletes a given Hotel entity
     *
     * @param hotel - Hotel entity
     */
    void delete(Hotel hotel);

    /**
     * Deletes the Hotel entity with the given id
     *
     * @param id - Hotel entity id
     */
    void deleteById(Long id);
}
