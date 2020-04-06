package com.it.service;

import com.it.component.LocalizedMessageSource;
import com.it.model.Customer;
import com.it.repository.CustomerRepository;
import com.it.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Contains tests for CustomerServiceImpl class
 */
@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private LocalizedMessageSource localizedMessageSource;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    public void testFindAll() {
        final List<Customer> customers = Collections.singletonList(new Customer());
        when(customerRepository.findAll()).thenReturn(customers);
        assertEquals(customers, customerService.findAll());
    }

    @Test
    public void testFindById() {
        final Customer customer = new Customer();
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        assertEquals(customer, customerService.findById(1L));
    }

    @Test
    public void testSave() {
        final Customer customer = new Customer();
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(customer);
        assertEquals(customer, customerService.save(customer));
    }

    @Test
    public void testUpdate() {
        final Customer customer = new Customer(1L);
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(customer);
        assertEquals(customer, customerService.update(customer));
    }

    @Test
    public void testDelete() {
        final Customer customer = new Customer();
        customer.setId(1L);
        doNothing().when(customerRepository).delete(customer);
        assertDoesNotThrow(() -> customerService.delete(customer));
    }

    @Test
    public void testDeleteById() {
        doNothing().when(customerRepository).deleteById(anyLong());
        assertDoesNotThrow(() -> customerService.deleteById(1L));
    }

    @Test
    public void testCustomerNotExistError() {
        Customer customer = new Customer();
        when(customerRepository.findById(anyLong())).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> customerService.findById(1L));
    }

    @Test
    public void testCustomerNotHaveIdError() {
        final Customer customer = new Customer();
        customer.setId(1L);
        assertThrows(RuntimeException.class, () -> customerService.save(customer));
    }

    @Test
    public void testCustomerHaveIdError() {
        final Customer customer = new Customer();
        assertThrows(RuntimeException.class, () -> customerService.update(customer));
    }

    @Test
    public void testCustomersContractNumberNotUniqueError() {
        final Customer customer = new Customer();
        customer.setCustomersContractNumber("524");
        when(customerRepository.existsByCustomersContractNumber("524")).thenReturn(true);
        assertThrows(RuntimeException.class, () -> customerService.save(customer));
    }

    @Test
    public void testCustomersPassportNumberNotUniqueError() {
        final Customer customer = new Customer();
        customer.setCustomersPassportNumber("524");
        when(customerRepository.existsByCustomersPassportNumber("524")).thenReturn(true);
        assertThrows(RuntimeException.class, () -> customerService.save(customer));
    }
}