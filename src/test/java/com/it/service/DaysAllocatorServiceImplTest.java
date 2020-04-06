package com.it.service;

import com.it.repository.OrderListRepository;
import com.it.service.impl.DaysAllocatorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * Contains tests for UserServiceImpl class
 */
@ExtendWith(MockitoExtension.class)
public class DaysAllocatorServiceImplTest {

    @InjectMocks
    private DaysAllocatorServiceImpl daysAllocatorService;

    @Mock
    private OrderListRepository orderListRepository;

    @Test
    public void testAllocateDaysInCurrentMonth() {
        when(orderListRepository.findBeginningDateOfTourById(anyLong())).thenReturn(LocalDate.of(2019, 10, 1));
        when(orderListRepository.findAmountOfDaysOfTourById(anyLong())).thenReturn(7);
        assertDoesNotThrow(() -> daysAllocatorService.allocateDays(1L));
    }

    @Test
    public void testAllocateDaysInDifferentMonths() {
        when(orderListRepository.findBeginningDateOfTourById(anyLong())).thenReturn(LocalDate.of(2019, 9, 28));
        when(orderListRepository.findAmountOfDaysOfTourById(anyLong())).thenReturn(10);
        assertDoesNotThrow(() -> daysAllocatorService.allocateDays(1L));
    }

    @Test
    public void testFindAmountOfDaysOfTourFromOrder() {
        when(orderListRepository.findAmountOfDaysOfTourById(anyLong())).thenReturn(7);
        assertDoesNotThrow(() -> daysAllocatorService.findAmountOfDaysOfTourFromOrder(1L));
    }

    @Test
    public void testFindBeginningDateOfTourFromOrder() {
        when(orderListRepository.findBeginningDateOfTourById(anyLong())).thenReturn(LocalDate.of(2019, 10, 1));
        assertDoesNotThrow(() -> daysAllocatorService.findBeginningDateOfTourFromOrder(1L));
    }

    @Test
    public void testGetDaysInCurrentMonth() {
        assertNull(daysAllocatorService.getDaysInCurrentMonth());
        assertDoesNotThrow(() -> daysAllocatorService.getDaysInCurrentMonth());
    }

    @Test
    public void testGetDaysInNextMonth() {
        assertNull(daysAllocatorService.getDaysInNextMonth());
        assertDoesNotThrow(() -> daysAllocatorService.getDaysInNextMonth());
    }

    @Test
    public void testGetMonthOfToursBeginning() {
        assertNull(daysAllocatorService.getMonthOfToursBeginning());
        assertDoesNotThrow(() -> daysAllocatorService.getMonthOfToursBeginning());
    }

    @Test
    public void testGetMonthOfToursEnding() {
        assertNull(daysAllocatorService.getMonthOfToursEnding());
        assertDoesNotThrow(() -> daysAllocatorService.getMonthOfToursEnding());
    }

    @Test
    public void testGetYearOfToursBeginning() {
        assertNull(daysAllocatorService.getYearOfToursBeginning());
        assertDoesNotThrow(() -> daysAllocatorService.getYearOfToursBeginning());
    }

    @Test
    public void testGetYearOfToursEnding() {
        assertNull(daysAllocatorService.getYearOfToursEnding());
        assertDoesNotThrow(() -> daysAllocatorService.getYearOfToursEnding());
    }
}