package com.it.service.impl;

import com.it.component.LocalizedMessageSource;
import com.it.config.utils.CacheName;
import com.it.model.HotelsAddress;
import com.it.repository.HotelsAddressRepository;
import com.it.service.HotelsAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of service interface for HotelsAddress entity
 */
@CacheConfig(cacheNames = {CacheName.HOTEL_ADDRESS})
@RequiredArgsConstructor
@Service
public class HotelsAddressServiceImpl implements HotelsAddressService {

    private final HotelsAddressRepository hotelsAddressRepository;
    private final LocalizedMessageSource localizedMessageSource;

    /**
     * Finds all HotelsAddress entities
     *
     * @return - List of HotelsAddress entities
     */
    @Override
    public List<HotelsAddress> findAll() {
        return hotelsAddressRepository.findAll();
    }

    /**
     * Finds the HotelsAddress entity with the given id
     *
     * @param id - HotelsAddress entity id
     * @return - HotelsAddress entity
     */
    @Cacheable(key = "#id")
    @Override
    public HotelsAddress findById(Long id) {
        return hotelsAddressRepository.findById(id).orElseThrow(() -> new RuntimeException(localizedMessageSource.getMessage("error.hotelsAddress.notExist", new Object[]{})));
    }

    /**
     * Saves a given HotelsAddress entity
     *
     * @param hotelsAddress - HotelsAddress Entity
     * @return - the saved HotelsAddress entity
     */
    @CachePut(key = "#result.id")
    @Transactional
    @Override
    public HotelsAddress save(HotelsAddress hotelsAddress) {
        validate(hotelsAddress.getId() != null, localizedMessageSource.getMessage("error.hotelsAddress.notHaveId", new Object[]{}));
        validate(hotelsAddressRepository.existsByCountryAndCityAndStreetAndBuildingNumber(hotelsAddress.getCountry(), hotelsAddress.getCity(), hotelsAddress.getStreet(), hotelsAddress.getBuildingNumber()),
                localizedMessageSource.getMessage("error.hotelsAddress.address.notUnique", new Object[]{}));
        return hotelsAddressRepository.saveAndFlush(hotelsAddress);
    }

    /**
     * Updates a HotelsAddress entity and flushes changes instantly
     *
     * @param hotelsAddress - HotelsAddress entity
     * @return - the updated HotelsAddress entity
     */
    @CachePut(key = "#result.id")
    @Transactional
    @Override
    public HotelsAddress update(HotelsAddress hotelsAddress) {
        final Long hotelsAddressId = hotelsAddress.getId();
        validate(hotelsAddressId == null, localizedMessageSource.getMessage("error.hotelsAddress.haveId", new Object[]{}));
        final HotelsAddress duplicateHotelsAddress = hotelsAddressRepository.findByCountryAndCityAndStreetAndBuildingNumber(hotelsAddress.getCountry(), hotelsAddress.getStreet(), hotelsAddress.getStreet(), hotelsAddress.getBuildingNumber());
        final boolean isDuplicateExist = duplicateHotelsAddress != null && !Objects.equals(hotelsAddressId, duplicateHotelsAddress.getId());
        validate(isDuplicateExist, localizedMessageSource.getMessage("error.hotelsAddress.address.notUnique", new Object[]{}));
        return hotelsAddressRepository.saveAndFlush(hotelsAddress);
    }

    /**
     * Deletes a given HotelsAddress entity
     *
     * @param hotelsAddress - HotelsAddress entity
     */
    @CacheEvict(key = "#hotelsAddress.id")
    @Transactional
    @Override
    public void delete(HotelsAddress hotelsAddress) {
        validate(hotelsAddress == null, localizedMessageSource.getMessage("error.hotelsAddress.haveId", new Object[]{}));
        hotelsAddressRepository.delete(hotelsAddress);
    }

    /**
     * Deletes the HotelsAddress entity with the given id
     *
     * @param id - HotelsAddress entity id
     */
    @CacheEvict(key = "#id")
    @Transactional
    @Override
    public void deleteById(Long id) {
        hotelsAddressRepository.deleteById(id);
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
