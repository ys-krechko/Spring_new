package com.it.service;

import com.it.model.HotelRoomHotel;

import java.util.List;

/**
 * Interface with CRUD methods for HotelRoomHotel entity
 */
public interface HotelRoomHotelService {

    /**
     * Finds all HotelRoomHotel entities
     *
     * @return - List of HotelRoomHotel entities
     */
    List<HotelRoomHotel> findAll();

    /**
     * Finds the HotelRoomHotel entity with the given id
     *
     * @param id - HotelRoomHotel entity id
     * @return - HotelRoomHotel entity
     */
    HotelRoomHotel findById(Long id);

    /**
     * Saves a given HotelRoomHotel entity
     *
     * @param hotelRoomHotel - HotelRoomHotel entity
     * @return - the saved HotelRoomHotel entity
     */
    HotelRoomHotel save(HotelRoomHotel hotelRoomHotel);

    /**
     * Updates a HotelRoomHotel entity and flushes changes instantly
     *
     * @param hotelRoomHotel - HotelRoomHotel entity
     * @return - the updated HotelRoomHotel entity
     */
    HotelRoomHotel update(HotelRoomHotel hotelRoomHotel);

    /**
     * Deletes a given HotelRoomHotel entity
     *
     * @param hotelRoomHotel - HotelRoomHotel entity
     */
    void delete(HotelRoomHotel hotelRoomHotel);

    /**
     * Deletes the HotelRoomHotel entity with the given id
     *
     * @param id - HotelRoomHotel entity id
     */
    void deleteById(Long id);
}
