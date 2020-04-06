package com.it.service.impl;

import com.it.component.LocalizedMessageSource;
import com.it.config.utils.CacheName;
import com.it.model.Insurance;
import com.it.repository.InsuranceRepository;
import com.it.service.InsuranceService;
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
 * Implementation of service interface for Insurance entity
 */
@CacheConfig(cacheNames = {CacheName.INSURANCE})
@RequiredArgsConstructor
@Service
public class InsuranceServiceImpl implements InsuranceService {

    private final InsuranceRepository insuranceRepository;
    private final LocalizedMessageSource localizedMessageSource;

    /**
     * Finds all Insurance entities
     *
     * @return - List of Insurance entities
     */
    @Override
    public List<Insurance> findAll() {
        return insuranceRepository.findAll();
    }

    /**
     * Finds the Insurance entity with the given id
     *
     * @param id - Insurance entity id
     * @return - Insurance entity
     */
    @Cacheable(key = "#id")
    @Override
    public Insurance findById(Long id) {
        return insuranceRepository.findById(id).orElseThrow(() -> new RuntimeException(localizedMessageSource.getMessage("error.insurance.notExist", new Object[]{})));
    }

    /**
     * Saves a given Insurance entity
     *
     * @param insurance - Insurance entity
     * @return - the saved Insurance entity
     */
    @CachePut(key = "#result.id")
    @Transactional
    @Override
    public Insurance save(Insurance insurance) {
        validate(insurance.getId() != null, localizedMessageSource.getMessage("error.insurance.notHaveId", new Object[]{}));
        validate(insuranceRepository.existsByInsuranceType(insurance.getInsuranceType()), localizedMessageSource.getMessage("error.insurance.type.notUnique", new Object[]{}));
        return insuranceRepository.saveAndFlush(insurance);
    }

    /**
     * Updates a Insurance entity and flushes changes instantly
     *
     * @param insurance - Insurance entity
     * @return - the updated Insurance entity
     */
    @CachePut(key = "#result.id")
    @Transactional
    @Override
    public Insurance update(Insurance insurance) {
        Long insuranceId = insurance.getId();
        validate(insuranceId == null, localizedMessageSource.getMessage("error.insurance.haveId", new Object[]{}));
        final Insurance duplicateInsurance = insuranceRepository.findByInsuranceType(insurance.getInsuranceType());
        final boolean isDuplicateExist = duplicateInsurance != null && !Objects.equals(duplicateInsurance.getId(), insuranceId);
        validate(isDuplicateExist, localizedMessageSource.getMessage("error.insurance.type.notUnique", new Object[]{}));
        return insuranceRepository.saveAndFlush(insurance);
    }

    /**
     * Deletes a given Insurance entity
     *
     * @param insurance - Insurance entity
     */
    @CacheEvict(key = "#insurance.id")
    @Transactional
    @Override
    public void delete(Insurance insurance) {
        validate(insurance.getId() == null, localizedMessageSource.getMessage("error.insurance.haveId", new Object[]{}));
        insuranceRepository.delete(insurance);
    }

    /**
     * Deletes the Insurance entity with the given id
     *
     * @param id - Insurance entity id
     */
    @CacheEvict(key = "#id")
    @Transactional
    @Override
    public void deleteById(Long id) {
        insuranceRepository.deleteById(id);
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
