package com.it.service;

import com.it.component.LocalizedMessageSource;
import com.it.model.Hotel;
import com.it.model.HotelsAddress;
import com.it.repository.HotelRepository;
import com.it.service.impl.HotelServiceImpl;
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
 * Contains tests for HotelServiceImpl class
 */
@ExtendWith(MockitoExtension.class)
public class HotelServiceImplTest {

    @InjectMocks
    private HotelServiceImpl hotelService;

    @Mock
    private HotelsAddressService hotelsAddressService;

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private LocalizedMessageSource localizedMessageSource;

    @Test
    public void testFindAll() {
        final List<Hotel> hotels = Collections.singletonList(new Hotel());
        when(hotelRepository.findAllWithHotelsAddress()).thenReturn(hotels);
        assertEquals(hotels, hotelService.findAll());
    }

    @Test
    public void testFindById() {
        final Hotel hotel = new Hotel();
        when(hotelRepository.findByIdWithHotelsAddress(anyLong())).thenReturn(Optional.of(hotel));
        assertEquals(hotel, hotelService.findById(1L));
    }

    @Test
    public void testSave() {
        final Hotel hotel = new Hotel();
        final HotelsAddress hotelsAddress = new HotelsAddress(1L);
        hotel.setHotelsAddress(hotelsAddress);
        when(hotelRepository.saveAndFlush(any(Hotel.class))).thenReturn(hotel);
        when(hotelsAddressService.findById(anyLong())).thenReturn(hotelsAddress);
        assertEquals(hotel, hotelService.save(hotel));
    }

    @Test
    public void testUpdate() {
        final Hotel hotel = new Hotel(1L);
        final HotelsAddress hotelsAddress = new HotelsAddress(1L);
        hotel.setHotelsAddress(hotelsAddress);
        when(hotelRepository.saveAndFlush(any(Hotel.class))).thenReturn(hotel);
        when(hotelsAddressService.findById(anyLong())).thenReturn(hotelsAddress);
        assertEquals(hotel, hotelService.update(hotel));
    }

    @Test
    public void testDelete() {
        final Hotel hotel = new Hotel();
        hotel.setId(1L);
        doNothing().when(hotelRepository).delete(hotel);
        assertDoesNotThrow(() -> hotelService.delete(hotel));
    }

    @Test
    public void testDeleteById() {
        doNothing().when(hotelRepository).deleteById(anyLong());
        assertDoesNotThrow(() -> hotelService.deleteById(1L));
    }
}