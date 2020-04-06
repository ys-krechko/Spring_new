package com.it.service.impl;

import com.it.component.LocalizedMessageSource;
import com.it.config.utils.CacheName;
import com.it.model.HotelRoom;
import com.it.repository.HotelRoomRepository;
import com.it.service.HotelRoomPriceService;
import com.it.service.HotelRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of service interface for HotelRoom entity
 */
@CacheConfig(cacheNames = {CacheName.HOTEL_ROOM})
@RequiredArgsConstructor
@Service
public class HotelRoomServiceImpl implements HotelRoomService {

    private final HotelRoomRepository hotelRoomRepository;
    private final LocalizedMessageSource localizedMessageSource;
    private final HotelRoomPriceService hotelRoomPriceService;

    /**
     * Finds all HotelRoom entities
     *
     * @return - List of HotelRoom entities
     */
    @Override
    public List<HotelRoom> findAll() {
        return hotelRoomRepository.findAllWithHotelRoomPrices();
    }

    /**
     * Finds the OHotelRoom entity with the given id
     *
     * @param id - HotelRoom entity id
     * @return - HotelRoom entity
     */
    @Cacheable(key = "#id")
    @Override
    public HotelRoom findById(Long id) {
        return hotelRoomRepository.findByIdWithHotelRoomPrices(id).orElseThrow(() -> new RuntimeException(localizedMessageSource.getMessage("error.hotelRoom.notExist", new Object[]{})));
    }

    /**
     * Saves a given HotelRoom entity
     *
     * @param hotelRoom - HotelRoom Entity
     * @return - the saved HotelRoom entity
     */
    @CachePut(key = "#result.id")
    @Transactional
    @Override
    public HotelRoom save(HotelRoom hotelRoom) {
        validate(hotelRoom.getId() != null, localizedMessageSource.getMessage("error.hotelRoom.notHaveId", new Object[]{}));
        return saveAndFlush(hotelRoom);
    }

    /**
     * Updates a HotelRoom entity and flushes changes instantly
     *
     * @param hotelRoom - HotelRoom entity
     * @return - the updated HotelRoom entity
     */
    @CachePut(key = "#result.id")
    @Transactional
    @Override
    public HotelRoom update(HotelRoom hotelRoom) {
        final Long hotelRoomId = hotelRoom.getId();
        validate(hotelRoomId == null, localizedMessageSource.getMessage("error.hotelRoom.haveId", new Object[]{}));
        return saveAndFlush(hotelRoom);
    }

    /**
     * Deletes a given HotelRoom entity
     *
     * @param hotelRoom - HotelRoom entity
     */
    @CacheEvict(key = "#hotelRoom.id")
    @Transactional
    @Override
    public void delete(HotelRoom hotelRoom) {
        validate(hotelRoom.getId() == null, localizedMessageSource.getMessage("error.hotelRoom.haveId", new Object[]{}));
        hotelRoomRepository.delete(hotelRoom);
    }

    /**
     * Deletes the HotelRoom entity with the given id
     *
     * @param id - HotelRoom entity id
     */
    @CacheEvict(key = "#id")
    @Transactional
    @Override
    public void deleteById(Long id) {
        hotelRoomRepository.deleteById(id);
    }

    /**
     * Saves a HotelRoom entity and flushes changes instantly.
     * Sets reference to a Set of HotelRoomPrice entities
     *
     * @param hotelRoom - HotelRoom entity
     * @return - the saved HotelRoom entity
     */
    private HotelRoom saveAndFlush(HotelRoom hotelRoom) {
        hotelRoom.getHotelRoomPrices().forEach(hotelRoomPrice -> {
            validate(hotelRoomPrice == null || hotelRoomPrice.getId() == null, localizedMessageSource.getMessage("error.hotelRoom.hotelRoomPrices.isNull", new Object[]{}));
            hotelRoomPrice.setDate(hotelRoomPriceService.findById(hotelRoomPrice.getId()).getDate());
            hotelRoomPrice.setPricePerNight(hotelRoomPriceService.findById(hotelRoomPrice.getId()).getPricePerNight());
        });
        return hotelRoomRepository.saveAndFlush(hotelRoom);
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
