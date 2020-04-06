package com.it.service.impl;

import com.it.component.LocalizedMessageSource;
import com.it.config.utils.CacheName;
import com.it.model.Customer;
import com.it.repository.CustomerRepository;
import com.it.service.CustomerService;
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
 * Implementation of service interface for Customer entity
 */
@CacheConfig(cacheNames = {CacheName.CUSTOMER})
@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final LocalizedMessageSource localizedMessageSource;

    /**
     * Finds all Customer entities
     *
     * @return - List of Customer entities
     */
    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    /**
     * Finds the Customer entity with the given id
     *
     * @param id - Customer entity id
     * @return - Customer entity
     */
    @Cacheable(key = "#id")
    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException(localizedMessageSource.getMessage("error.customer.notExist", new Object[]{})));
    }

    /**
     * Saves a given Customer entity
     *
     * @param customer - Customer Entity
     * @return - the saved Customer entity
     */
    @CachePut(key = "#result.id")
    @Transactional
    @Override
    public Customer save(Customer customer) {
        validate(customer.getId() != null, localizedMessageSource.getMessage("error.customer.notHaveId", new Object[]{}));
        validate(customerRepository.existsByCustomersContractNumber(customer.getCustomersContractNumber()), localizedMessageSource.getMessage("error.customer.customersContractNumber.notUnique", new Object[]{}));
        validate(customerRepository.existsByCustomersPassportNumber(customer.getCustomersPassportNumber()), localizedMessageSource.getMessage("error.customer.customersPassportNumber.notUnique", new Object[]{}));
        return customerRepository.saveAndFlush(customer);

    }

    /**
     * Updates a Customer entity and flushes changes instantly
     *
     * @param customer - Customer entity
     * @return - the updated Customer entity
     */
    @CachePut(key = "#result.id")
    @Transactional
    @Override
    public Customer update(Customer customer) {
        final Long customerId = customer.getId();
        validate(customerId == null, localizedMessageSource.getMessage("error.customer.haveId", new Object[]{}));
        final Customer duplicateCustomerContractNumber = customerRepository.findByCustomersContractNumber(customer.getCustomersContractNumber());
        final Customer duplicateCustomerPassportNumber = customerRepository.findByCustomersPassportNumber(customer.getCustomersPassportNumber());
        final boolean isDuplicateCustomerContractNumberExist = duplicateCustomerContractNumber != null && !Objects.equals(duplicateCustomerContractNumber.getId(), customerId);
        final boolean isDuplicateCustomerPassportNumberExist = duplicateCustomerPassportNumber != null && !Objects.equals(duplicateCustomerPassportNumber.getId(), customerId);
        validate(isDuplicateCustomerContractNumberExist, localizedMessageSource.getMessage("error.customer.customersContractNumber.notUnique", new Object[]{}));
        validate(isDuplicateCustomerPassportNumberExist, localizedMessageSource.getMessage("error.customer.customersPassportNumber.notUnique", new Object[]{}));
        return customerRepository.saveAndFlush(customer);
    }

    /**
     * Deletes a given Customer entity
     *
     * @param customer - Hotel entity
     */
    @CacheEvict(key = "#customer.id")
    @Transactional
    @Override
    public void delete(Customer customer) {
        validate(customer.getId() == null, localizedMessageSource.getMessage("error.customer.haveId", new Object[]{}));
        customerRepository.delete(customer);
    }

    /**
     * Deletes the Customer entity with the given id
     *
     * @param id - Customer entity id
     */
    @CacheEvict(key = "#id")
    @Transactional
    @Override
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
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
