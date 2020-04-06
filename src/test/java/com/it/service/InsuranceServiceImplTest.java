package com.it.service;

import com.it.component.LocalizedMessageSource;
import com.it.model.Insurance;
import com.it.repository.InsuranceRepository;
import com.it.service.impl.InsuranceServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Contains tests for InsuranceServiceImpl class
 */
@ExtendWith(MockitoExtension.class)
public class InsuranceServiceImplTest {

    @InjectMocks
    private InsuranceServiceImpl insuranceService;

    @Mock
    private InsuranceRepository insuranceRepository;

    @Mock
    private LocalizedMessageSource localizedMessageSource;

    @Test
    public void testFindAll() {
        final List<Insurance> insurances = Collections.singletonList(new Insurance());
        when(insuranceRepository.findAll()).thenReturn(insurances);
        assertEquals(insurances, insuranceService.findAll());
    }

    @Test
    public void testFindById() {
        Insurance insurance = new Insurance();
        when(insuranceRepository.findById(anyLong())).thenReturn(Optional.of(insurance));
        assertEquals(insurance, insuranceService.findById(1L));
    }

    @Test
    public void testSave() {
        Insurance insurance = new Insurance();
        when(insuranceRepository.saveAndFlush(any(Insurance.class))).thenReturn(insurance);
        assertEquals(insurance, insuranceService.save(insurance));
    }

    @Test
    public void testUpdate() {
        Insurance insurance = new Insurance(1L);
        when(insuranceRepository.saveAndFlush(any(Insurance.class))).thenReturn(insurance);
        assertEquals(insurance, insuranceService.update(insurance));
    }

    @Test
    public void testDelete() {
        Insurance insurance = new Insurance();
        insurance.setId(1L);
        doNothing().when(insuranceRepository).delete(insurance);
        assertDoesNotThrow(() -> insuranceService.delete(insurance));
    }

    @Test
    public void testDeleteById() {
        doNothing().when(insuranceRepository).deleteById(anyLong());
        assertDoesNotThrow(() -> insuranceService.deleteById(1L));
    }
}