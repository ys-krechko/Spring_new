package com.it.service;

import com.it.model.Insurance;

import java.util.List;

/**
 * Interface with CRUD methods for Insurance entity
 */
public interface InsuranceService {

    /**
     * Finds all Insurance entities
     *
     * @return - List of Insurance entities
     */
    List<Insurance> findAll();

    /**
     * Finds the Insurance entity with the given id
     *
     * @param id - Insurance entity id
     * @return - Insurance entity
     */
    Insurance findById(Long id);

    /**
     * Saves a given Insurance entity
     *
     * @param insurance - Insurance entity
     * @return - the saved Insurance entity
     */
    Insurance save(Insurance insurance);

    /**
     * Updates a Insurance entity and flushes changes instantly
     *
     * @param insurance - Insurance entity
     * @return - the updated Insurance entity
     */
    Insurance update(Insurance insurance);

    /**
     * Deletes a given Insurance entity
     *
     * @param insurance - Insurance entity
     */
    void delete(Insurance insurance);

    /**
     * Deletes the Insurance entity with the given id
     *
     * @param id - Insurance entity id
     */
    void deleteById(Long id);
}
