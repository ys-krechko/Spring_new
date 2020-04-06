package com.it.service.impl;

import com.it.component.LocalizedMessageSource;
import com.it.config.utils.CacheName;
import com.it.model.HotelRoomPrice;
import com.it.repository.HotelRoomPriceRepository;
import com.it.service.HotelRoomPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of service interface for HotelRoomPrice entity
 */
@CacheConfig(cacheNames = {CacheName.HOTEL_ROOM_PRICE})
@RequiredArgsConstructor
@Service
public class HotelRoomPriceServiceImpl implements HotelRoomPriceService {

    private final HotelRoomPriceRepository hotelRoomPriceRepository;
    private final LocalizedMessageSource localizedMessageSource;

    /**
     * Finds all HotelRoomPrice entities
     *
     * @return - List of HotelRoomPrice entities
     */
    @Override
    public List<HotelRoomPrice> findAll() {
        return hotelRoomPriceRepository.findAll();
    }

    /**
     * Finds the HotelRoomPrice entity with the given id
     *
     * @param id - HotelRoomPrice entity id
     * @return - HotelRoomPrice entity
     */
    @Cacheable(key = "#id")
    @Override
    public HotelRoomPrice findById(Long id) {
        return hotelRoomPriceRepository.findById(id).orElseThrow(() -> new RuntimeException(localizedMessageSource.getMessage("error.hotelRoomPrices.notExist", new Object[]{})));
    }

    /**
     * Saves a given HotelRoomPrice entity
     *
     * @param hotelRoomPrice - HotelRoomPrice entity
     * @return - the saved HotelRoomPrice entity
     */
    @CachePut(key = "#result.id")
    @Transactional
    @Override
    public HotelRoomPrice save(HotelRoomPrice hotelRoomPrice) {
        validate(hotelRoomPrice.getId() != null, localizedMessageSource.getMessage("error.hotelRoomPrices.notHaveId", new Object[]{}));
        validate(hotelRoomPrice.getDate().getDayOfMonth() != 1, localizedMessageSource.getMessage("error.hotelRoomPrices.wrongDate", new Object[]{}));
        return hotelRoomPriceRepository.saveAndFlush(hotelRoomPrice);
    }

    /**
     * Updates a HotelRoomPrice entity and flushes changes instantly
     *
     * @param hotelRoomPrice - HotelRoomPrice entity
     * @return - the updated HotelRoomPrice entity
     */
    @CachePut(key = "#result.id")
    @Transactional
    @Override
    public HotelRoomPrice update(HotelRoomPrice hotelRoomPrice) {
        final Long hotelRoomPriceId = hotelRoomPrice.getId();
        validate(hotelRoomPriceId == null, localizedMessageSource.getMessage("error.hotelRoomPrices.haveId", new Object[]{}));
        validate(hotelRoomPrice.getDate().getDayOfMonth() != 1, localizedMessageSource.getMessage("error.hotelRoomPrices.wrongDate", new Object[]{}));
        return hotelRoomPriceRepository.saveAndFlush(hotelRoomPrice);
    }

    /**
     * Deletes a given HotelRoomPrice entity
     *
     * @param hotelRoomPrice - HotelRoomPrice entity
     */
    @CacheEvict(key = "#hotelRoomPrice.id")
    @Transactional
    @Override
    public void delete(HotelRoomPrice hotelRoomPrice) {
        validate(hotelRoomPrice.getId() == null, localizedMessageSource.getMessage("error.hotelRoomPrices.haveId", new Object[]{}));
        hotelRoomPriceRepository.delete(hotelRoomPrice);
    }

    /**
     * Deletes the HotelRoomPrice entity with the given id
     *
     * @param id - HotelRoomPrice entity id
     */
    @CacheEvict(key = "#id")
    @Transactional
    @Override
    public void deleteById(Long id) {
        hotelRoomPriceRepository.deleteById(id);
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
