package com.it.service;

import com.it.model.HotelRoomPrice;

import java.util.List;

/**
 * Interface with CRUD methods for HotelRoomPrice entity
 */
public interface HotelRoomPriceService {

    /**
     * Finds all HotelRoomPrice entities
     *
     * @return - List of HotelRoomPrice entities
     */
    List<HotelRoomPrice> findAll();

    /**
     * Finds the HotelRoomPrice entity with the given id
     *
     * @param id - HotelRoomPrice entity id
     * @return - HotelRoomPrice entity
     */
    HotelRoomPrice findById(Long id);

    /**
     * Saves a given HotelRoomPrice entity
     *
     * @param hotelRoomPrice - HotelRoomPrice entity
     * @return - the saved HotelRoomPrice entity
     */
    HotelRoomPrice save(HotelRoomPrice hotelRoomPrice);

    /**
     * Updates a HotelRoomPrice entity and flushes changes instantly
     *
     * @param hotelRoomPrice - HotelRoomPrice entity
     * @return - the updated HotelRoomPrice entity
     */
    HotelRoomPrice update(HotelRoomPrice hotelRoomPrice);

    /**
     * Deletes a given HotelRoomPrice entity
     *
     * @param hotelRoomPrice - HotelRoomPrice entity
     */
    void delete(HotelRoomPrice hotelRoomPrice);

    /**
     * Deletes the HotelRoomPrice entity with the given id
     *
     * @param id - HotelRoomPrice entity id
     */
    void deleteById(Long id);
}
