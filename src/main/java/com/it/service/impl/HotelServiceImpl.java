package com.it.service.impl;

import com.it.component.LocalizedMessageSource;
import com.it.config.utils.CacheName;
import com.it.model.Hotel;
import com.it.model.HotelsAddress;
import com.it.repository.HotelRepository;
import com.it.service.HotelService;
import com.it.service.HotelsAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of service interface for Hotel entity
 */
@CacheConfig(cacheNames = {CacheName.HOTEL})
@RequiredArgsConstructor
@Service
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final HotelsAddressService hotelsAddressService;
    private final LocalizedMessageSource localizedMessageSource;

    /**
     * Finds all Hotel entities
     *
     * @return - List of Hotel entities
     */
    @Override
    public List<Hotel> findAll() {
        return hotelRepository.findAllWithHotelsAddress();
    }

    /**
     * Finds the Hotel entity with the given id
     *
     * @param id - Hotel entity id
     * @return - Hotel entity
     */
    @Cacheable(key = "#id")
    @Override
    public Hotel findById(Long id) {
        return hotelRepository.findByIdWithHotelsAddress(id).orElseThrow(() -> new RuntimeException(localizedMessageSource.getMessage("error.hotel.notExist", new Object[]{})));
    }

    /**
     * Saves a given Hotel entity
     *
     * @param hotel - Hotel Entity
     * @return - the saved Hotel entity
     */
    @CachePut(key = "#result.id")
    @Transactional
    @Override
    public Hotel save(Hotel hotel) {
        validate(hotel.getId() != null, localizedMessageSource.getMessage("error.hotel.notHaveId", new Object[]{}));
        return saveAndFlush(hotel);
    }

    /**
     * Updates a Hotel entity and flushes changes instantly
     *
     * @param hotel - Hotel entity
     * @return - the updated Hotel entity
     */
    @CachePut(key = "#result.id")
    @Transactional
    @Override
    public Hotel update(Hotel hotel) {
        validate(hotel.getId() == null, localizedMessageSource.getMessage("error.hotel.haveId", new Object[]{}));
        return saveAndFlush(hotel);
    }

    /**
     * Deletes a given Hotel entity
     *
     * @param hotel - Hotel entity
     */
    @CacheEvict(key = "#hotel.id")
    @Transactional
    @Override
    public void delete(Hotel hotel) {
        validate(hotel.getId() == null, localizedMessageSource.getMessage("error.hotel.haveId", new Object[]{}));
        hotelRepository.delete(hotel);
    }

    /**
     * Deletes the Hotel entity with the given id
     *
     * @param id - Hotel entity id
     */
    @CacheEvict(key = "#id")
    @Transactional
    @Override
    public void deleteById(Long id) {
        hotelRepository.deleteById(id);
    }

    /**
     * Saves the Hotel entity and flushes changes instantly.
     * Sets reference to the HotelsAddress entity
     *
     * @param hotel - Hotel entity
     * @return - the saved Hotel entity
     */
    private Hotel saveAndFlush(Hotel hotel) {
        validate(hotel.getHotelsAddress() == null || hotel.getHotelsAddress().getId() == null, localizedMessageSource.getMessage("error.hotel.hotelsAddress.isNull", new Object[]{}));
        final HotelsAddress hotelsAddress = hotelsAddressService.findById(hotel.getHotelsAddress().getId());
        validate(hotelsAddress.getHotel() != null, localizedMessageSource.getMessage("error.hotel.hotelsAddress.notUnique", new Object[]{}));
        hotel.setHotelsAddress(hotelsAddress);
        return hotelRepository.saveAndFlush(hotel);
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
