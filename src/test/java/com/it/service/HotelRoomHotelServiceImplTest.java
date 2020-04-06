package com.it.service;

import com.it.component.LocalizedMessageSource;
import com.it.model.Hotel;
import com.it.model.HotelRoom;
import com.it.model.HotelRoomHotel;
import com.it.repository.HotelRoomHotelRepository;
import com.it.service.impl.HotelRoomHotelServiceImpl;
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
 * Contains tests for HotelRoomHotelServiceImpl class
 */
@ExtendWith(MockitoExtension.class)
public class HotelRoomHotelServiceImplTest {

    @InjectMocks
    private HotelRoomHotelServiceImpl hotelRoomHotelService;

    @Mock
    private HotelRoomHotelRepository hotelRoomHotelRepository;

    @Mock
    private HotelRoomService hotelRoomService;

    @Mock
    private HotelService hotelService;

    @Mock
    private LocalizedMessageSource localizedMessageSource;

    @Test
    public void testFindAll() {
        final List<HotelRoomHotel> hotelRoomHotels = Collections.singletonList(new HotelRoomHotel());
        when(hotelRoomHotelRepository.findAllWithHotelAndAndHotelRoom()).thenReturn(hotelRoomHotels);
        assertEquals(hotelRoomHotels, hotelRoomHotelService.findAll());
    }

    @Test
    public void testFindById() {
        final HotelRoomHotel hotelRoomHotel = new HotelRoomHotel();
        when(hotelRoomHotelRepository.findByIdWithHotelAndAndHotelRoom(anyLong())).thenReturn(Optional.of(hotelRoomHotel));
        assertEquals(hotelRoomHotel, hotelRoomHotelService.findById(1L));
    }

    @Test
    public void testSave() {
        final HotelRoomHotel hotelRoomHotel = new HotelRoomHotel();
        final Hotel hotel = new Hotel(1L);
        final HotelRoom hotelRoom = new HotelRoom(1L);
        hotelRoomHotel.setHotel(hotel);
        hotelRoomHotel.setHotelRoom(hotelRoom);
        when(hotelRoomHotelRepository.saveAndFlush(any(HotelRoomHotel.class))).thenReturn(hotelRoomHotel);
        when(hotelService.findById(anyLong())).thenReturn(hotel);
        when(hotelRoomService.findById(anyLong())).thenReturn(hotelRoom);
        assertEquals(hotelRoomHotel, hotelRoomHotelService.save(hotelRoomHotel));
    }

    @Test
    public void testUpdate() {
        final HotelRoomHotel hotelRoomHotel = new HotelRoomHotel(1L);
        final Hotel hotel = new Hotel(1L);
        final HotelRoom hotelRoom = new HotelRoom(1L);
        hotelRoomHotel.setHotel(hotel);
        hotelRoomHotel.setHotelRoom(hotelRoom);
        when(hotelRoomHotelRepository.saveAndFlush(any(HotelRoomHotel.class))).thenReturn(hotelRoomHotel);
        when(hotelService.findById(anyLong())).thenReturn(hotel);
        when(hotelRoomService.findById(anyLong())).thenReturn(hotelRoom);
        assertEquals(hotelRoomHotel, hotelRoomHotelService.update(hotelRoomHotel));
    }

    @Test
    public void testDelete() {
        final HotelRoomHotel hotelRoomHotel = new HotelRoomHotel();
        hotelRoomHotel.setId(1L);
        doNothing().when(hotelRoomHotelRepository).delete(hotelRoomHotel);
        assertDoesNotThrow(() -> hotelRoomHotelService.delete(hotelRoomHotel));
    }

    @Test
    public void testDeleteById() {
        doNothing().when(hotelRoomHotelRepository).deleteById(anyLong());
        assertDoesNotThrow(() -> hotelRoomHotelService.deleteById(1L));
    }
}