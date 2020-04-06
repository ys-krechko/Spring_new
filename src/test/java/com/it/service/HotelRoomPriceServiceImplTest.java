package com.it.service;

import com.it.component.LocalizedMessageSource;
import com.it.model.HotelRoomPrice;
import com.it.repository.HotelRoomPriceRepository;
import com.it.service.impl.HotelRoomPriceServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
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
 * Contains tests for HotelRoomPriceServiceImpl class
 */
@ExtendWith(MockitoExtension.class)
public class HotelRoomPriceServiceImplTest {

    @InjectMocks
    private HotelRoomPriceServiceImpl hotelRoomPriceService;

    @Mock
    private HotelRoomPriceRepository hotelRoomPriceRepository;

    @Mock
    private LocalizedMessageSource localizedMessageSource;

    @Test
    public void testFindAll() {
        final List<HotelRoomPrice> hotelRoomPrices = Collections.singletonList(new HotelRoomPrice());
        when(hotelRoomPriceRepository.findAll()).thenReturn(hotelRoomPrices);
        assertEquals(hotelRoomPrices, hotelRoomPriceService.findAll());
    }

    @Test
    public void testFindById() {
        final HotelRoomPrice hotelRoomPrice = new HotelRoomPrice();
        when(hotelRoomPriceRepository.findById(anyLong())).thenReturn(Optional.of(hotelRoomPrice));
        assertEquals(hotelRoomPrice, hotelRoomPriceService.findById(1L));
    }

    @Test
    public void testSave() {
        final HotelRoomPrice hotelRoomPrice = new HotelRoomPrice();
        hotelRoomPrice.setDate(LocalDate.of(2019, 12, 1));
        when(hotelRoomPriceRepository.saveAndFlush(any(HotelRoomPrice.class))).thenReturn(hotelRoomPrice);
        assertEquals(hotelRoomPrice, hotelRoomPriceService.save(hotelRoomPrice));
    }

    @Test
    public void testUpdate() {
        final HotelRoomPrice hotelRoomPrice = new HotelRoomPrice(1L);
        hotelRoomPrice.setDate(LocalDate.of(2019, 12, 1));
        when(hotelRoomPriceRepository.saveAndFlush(any(HotelRoomPrice.class))).thenReturn(hotelRoomPrice);
        assertEquals(hotelRoomPrice, hotelRoomPriceService.update(hotelRoomPrice));
    }

    @Test
    public void testDelete() {
        final HotelRoomPrice hotelRoomPrice = new HotelRoomPrice();
        hotelRoomPrice.setId(1L);
        doNothing().when(hotelRoomPriceRepository).delete(hotelRoomPrice);
        assertDoesNotThrow(() -> hotelRoomPriceService.delete(hotelRoomPrice));
    }

    @Test
    public void testDeleteById() {
        doNothing().when(hotelRoomPriceRepository).deleteById(anyLong());
        assertDoesNotThrow(() -> hotelRoomPriceService.deleteById(1L));
    }
}