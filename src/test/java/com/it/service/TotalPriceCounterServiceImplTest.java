package com.it.service;

import com.it.repository.HotelRoomRepository;
import com.it.repository.OrderListRepository;
import com.it.service.impl.TotalPriceCounterServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Contains tests for TotalPriceCounterServiceImpl class
 */
@ExtendWith(MockitoExtension.class)
public class TotalPriceCounterServiceImplTest {

    @InjectMocks
    private TotalPriceCounterServiceImpl totalPriceCounterService;

    @Mock
    private OrderListRepository orderListRepository;

    @Mock
    private HotelRoomRepository hotelRoomRepository;

    @Mock
    private DaysAllocatorService daysAllocatorService;

    @Test
    public void testCountTotalPrice() {
        doNothing().when(daysAllocatorService).allocateDays(anyLong());
        when(daysAllocatorService.getDaysInCurrentMonth()).thenReturn(7);
        when(daysAllocatorService.getDaysInNextMonth()).thenReturn(1);
        when(daysAllocatorService.getMonthOfToursBeginning()).thenReturn(1);
        when(daysAllocatorService.getMonthOfToursEnding()).thenReturn(2);
        when(daysAllocatorService.getYearOfToursBeginning()).thenReturn(2019);
        when(daysAllocatorService.getYearOfToursEnding()).thenReturn(2019);
        when(orderListRepository.findHotelRoomIdByOrderListId(anyLong())).thenReturn(1L);
        when(hotelRoomRepository.findHotelRoomsPriceByHotelRoomsIdAndDate(anyLong(), any(LocalDate.class))).thenReturn(2.50);
        when(orderListRepository.findInsurancePriceByOrderListId(anyLong())).thenReturn(10.00);
        when(orderListRepository.findNumberOfTourists(anyLong())).thenReturn(2);
        assertEquals(40.00, totalPriceCounterService.countTotalPrice(1L));
    }

    @Test
    public void testCountPriceFromDaysInCurrentMonth() {
        when(daysAllocatorService.getMonthOfToursBeginning()).thenReturn(1);
        when(daysAllocatorService.getYearOfToursBeginning()).thenReturn(2019);
        when(daysAllocatorService.getDaysInCurrentMonth()).thenReturn(2);
        when(orderListRepository.findHotelRoomIdByOrderListId(anyLong())).thenReturn(1L);
        when(hotelRoomRepository.findHotelRoomsPriceByHotelRoomsIdAndDate(anyLong(), any(LocalDate.class))).thenReturn(2.50);
        assertDoesNotThrow(() -> totalPriceCounterService.countPriceFromDaysInCurrentMonth(1L));
    }

    @Test
    public void testCountPriceFromDaysInNextMonth() {
        when(daysAllocatorService.getMonthOfToursEnding()).thenReturn(2);
        when(daysAllocatorService.getYearOfToursEnding()).thenReturn(2019);
        when(daysAllocatorService.getDaysInNextMonth()).thenReturn(10);
        when(orderListRepository.findHotelRoomIdByOrderListId(anyLong())).thenReturn(1L);
        when(hotelRoomRepository.findHotelRoomsPriceByHotelRoomsIdAndDate(anyLong(), any(LocalDate.class))).thenReturn(2.10);
        assertDoesNotThrow(() -> totalPriceCounterService.countPriceFromDaysInNextMonth(1L));
    }

    @Test
    public void testFindHotelRoomIdFromHotelRoomHotelByOrderId() {
        when(orderListRepository.findHotelRoomIdByOrderListId(anyLong())).thenReturn(1L);
        assertEquals(1L, totalPriceCounterService.findHotelRoomIdFromHotelRoomHotelByOrderId(1L));
    }

    @Test
    public void testFindHotelRoomPriceByDateAndHotelRoomId() {
        when(hotelRoomRepository.findHotelRoomsPriceByHotelRoomsIdAndDate(anyLong(), any(LocalDate.class))).thenReturn(2.10);
        assertEquals(2.10, totalPriceCounterService.findHotelRoomPriceByDateAndHotelRoomId(1L, 2019, 01));
    }

    @Test
    public void testFindInsurancePriceByOrderId() {
        when(orderListRepository.findInsurancePriceByOrderListId(anyLong())).thenReturn(10.00);
        assertEquals(10.00, totalPriceCounterService.findInsurancePriceByOrderId(1L));
    }

    @Test
    public void testFindNumberOfTouristsFromOrderListById() {
        when(orderListRepository.findNumberOfTourists(anyLong())).thenReturn(2);
        assertEquals(2, totalPriceCounterService.findNumberOfTouristsFromOrderListById(1L));
    }
}