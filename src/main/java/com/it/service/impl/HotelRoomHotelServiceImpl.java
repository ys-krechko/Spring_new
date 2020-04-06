package com.it.service.impl;

import com.it.component.LocalizedMessageSource;
import com.it.config.utils.CacheName;
import com.it.model.HotelRoomHotel;
import com.it.repository.HotelRoomHotelRepository;
import com.it.service.HotelRoomHotelService;
import com.it.service.HotelRoomService;
import com.it.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of service interface for HotelRoomHotel entity
 */
@CacheConfig(cacheNames = {CacheName.HOTEL_ROOM_HOTEL})
@RequiredArgsConstructor
@Service
public class HotelRoomHotelServiceImpl implements HotelRoomHotelService {

    private final LocalizedMessageSource localizedMessageSource;
    private final HotelRoomHotelRepository hotelRoomHotelRepository;
    private final HotelService hotelService;
    private final HotelRoomService hotelRoomService;

    /**
     * Finds all HotelRoomHotel entities
     *
     * @return - List of HotelRoomHotel entities
     */
    @Override
    public List<HotelRoomHotel> findAll() {
        return hotelRoomHotelRepository.findAllWithHotelAndAndHotelRoom();
    }

    /**
     * Finds the HotelRoomHotel entity with the given id
     *
     * @param id - HotelRoomHotel entity id
     * @return - HotelRoomHotel entity
     */
    @Cacheable(key = "#id")
    @Override
    public HotelRoomHotel findById(Long id) {
        return hotelRoomHotelRepository.findByIdWithHotelAndAndHotelRoom(id).orElseThrow(() -> new RuntimeException(localizedMessageSource.getMessage("error.hotelRoomHotel.notExist", new Object[]{})));
    }

    /**
     * Saves a given HotelRoomHotel entity
     *
     * @param hotelRoomHotel - HotelRoomHotel entity
     * @return - the saved HotelRoomHotel entity
     */
    @CachePut(key = "#result.id")
    @Transactional
    @Override
    public HotelRoomHotel save(HotelRoomHotel hotelRoomHotel) {
        validate(hotelRoomHotel.getId() != null, localizedMessageSource.getMessage("error.hotelRoomHotel.notHaveId", new Object[]{}));
        return saveAndFlush(hotelRoomHotel);
    }

    /**
     * Updates a HotelRoomHotel entity and flushes changes instantly
     *
     * @param hotelRoomHotel - HotelRoomHotel entity
     * @return - the updated HotelRoomHotel entity
     */
    @CachePut(key = "#result.id")
    @Transactional
    @Override
    public HotelRoomHotel update(HotelRoomHotel hotelRoomHotel) {
        validate(hotelRoomHotel.getId() == null, localizedMessageSource.getMessage("error.hotelRoomHotel.haveId", new Object[]{}));
        return saveAndFlush(hotelRoomHotel);
    }

    /**
     * Deletes a given HotelRoomHotel entity
     *
     * @param hotelRoomHotel - HotelRoomHotel entity
     */
    @CacheEvict(key = "#hotelRoomHotel.id")
    @Transactional
    @Override
    public void delete(HotelRoomHotel hotelRoomHotel) {
        validate(hotelRoomHotel.getId() == null, localizedMessageSource.getMessage("error.hotelRoomHotel.haveId", new Object[]{}));
        hotelRoomHotelRepository.delete(hotelRoomHotel);
    }

    /**
     * Deletes the HotelRoomHotel entity with the given id
     *
     * @param id - HotelRoomHotel entity id
     */
    @CacheEvict(key = "#id")
    @Transactional
    @Override
    public void deleteById(Long id) {
        hotelRoomHotelRepository.deleteById(id);
    }

    /**
     * Saves the HotelRoomHotel entity and flushes changes instantly.
     * Sets reference to the Hotel and HotelRoom entities
     *
     * @param hotelRoomHotel - HotelRoomHotel entity
     * @return - the saved HotelRoomHotel entity
     */
    private HotelRoomHotel saveAndFlush(HotelRoomHotel hotelRoomHotel) {
        validate(hotelRoomHotel.getHotel() == null || hotelRoomHotel.getHotel().getId() == null, localizedMessageSource.getMessage("error.hotelRoomHotel.hotel.isNull", new Object[]{}));
        validate(hotelRoomHotel.getHotelRoom() == null || hotelRoomHotel.getHotelRoom().getId() == null, localizedMessageSource.getMessage("error.hotelRoomHotel.hotelRoom.isNull", new Object[]{}));
        hotelRoomHotel.setHotel(hotelService.findById(hotelRoomHotel.getHotel().getId()));
        hotelRoomHotel.setHotelRoom(hotelRoomService.findById(hotelRoomHotel.getHotelRoom().getId()));
        return hotelRoomHotelRepository.saveAndFlush(hotelRoomHotel);
    }

    /**
     * Checks, if passed parameter(s) matches the given condition
     *
     * @param expression   - condition for checking
     * @param errorMessage - message to be thrown in RuntimeException, if checking fails
     */
    private void validate(boolean expression, String errorMessage) {
        if (expression) {
            throw new RuntimeException(errorMessage);
        }
    }
}
