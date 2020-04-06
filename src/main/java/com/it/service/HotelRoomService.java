package com.it.service;

import com.it.model.HotelRoom;

import java.util.List;

/**
 * Interface with CRUD methods for HotelRoom entity
 */
public interface HotelRoomService {

    /**
     * Finds all HotelRoom entities
     *
     * @return - List of HotelRoom entities
     */
    List<HotelRoom> findAll();

    /**
     * Finds the OHotelRoom entity with the given id
     *
     * @param id - HotelRoom entity id
     * @return - HotelRoom entity
     */
    HotelRoom findById(Long id);

    /**
     * Saves a given HotelRoom entity
     *
     * @param hotelRoom - HotelRoom Entity
     * @return - the saved HotelRoom entity
     */
    HotelRoom save(HotelRoom hotelRoom);

    /**
     * Updates a HotelRoom entity and flushes changes instantly
     *
     * @param hotelRoom - HotelRoom entity
     * @return - the updated HotelRoom entity
     */
    HotelRoom update(HotelRoom hotelRoom);

    /**
     * Deletes a given HotelRoom entity
     *
     * @param hotelRoom - HotelRoom entity
     */
    void delete(HotelRoom hotelRoom);

    /**
     * Deletes the HotelRoom entity with the given id
     *
     * @param id - HotelRoom entity id
     */
    void deleteById(Long id);
}
