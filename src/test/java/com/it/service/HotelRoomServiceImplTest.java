package com.it.service;

import com.it.component.LocalizedMessageSource;
import com.it.model.HotelRoom;
import com.it.model.HotelRoomPrice;
import com.it.repository.HotelRoomRepository;
import com.it.service.impl.HotelRoomServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Contains tests for HotelRoomServiceImpl class
 */
@ExtendWith(MockitoExtension.class)
public class HotelRoomServiceImplTest {

    @InjectMocks
    private HotelRoomServiceImpl hotelRoomService;

    @Mock
    private HotelRoomRepository hotelRoomRepository;

    @Mock
    private HotelRoomPriceService hotelRoomPriceService;

    @Mock
    private LocalizedMessageSource localizedMessageSource;

    @Test
    public void testFindAll() {
        final List<HotelRoom> hotelRooms = Collections.singletonList(new HotelRoom());
        when(hotelRoomRepository.findAllWithHotelRoomPrices()).thenReturn(hotelRooms);
        assertEquals(hotelRooms, hotelRoomService.findAll());
    }

    @Test
    public void testFindById() {
        final HotelRoom hotelRoom = new HotelRoom();
        when(hotelRoomRepository.findByIdWithHotelRoomPrices(anyLong())).thenReturn(Optional.of(hotelRoom));
        assertEquals(hotelRoom, hotelRoomService.findById(1L));
    }

    @Test
    public void testSave() {
        final HotelRoom hotelRoom = new HotelRoom();
        final HotelRoomPrice hotelRoomPrice = new HotelRoomPrice(1L);
        final Set<HotelRoomPrice> hotelRoomPrices = Collections.singleton(hotelRoomPrice);
        hotelRoom.setHotelRoomPrices(hotelRoomPrices);
        when(hotelRoomRepository.saveAndFlush(any(HotelRoom.class))).thenReturn(hotelRoom);
        when(hotelRoomPriceService.findById(anyLong())).thenReturn(hotelRoomPrice);
        assertEquals(hotelRoom, hotelRoomService.save(hotelRoom));
    }

    @Test
    public void testUpdate() {
        final HotelRoom hotelRoom = new HotelRoom(1L);
        final HotelRoomPrice hotelRoomPrice = new HotelRoomPrice(1L);
        final Set<HotelRoomPrice> hotelRoomPrices = Collections.singleton(hotelRoomPrice);
        hotelRoom.setHotelRoomPrices(hotelRoomPrices);
        when(hotelRoomRepository.saveAndFlush(any(HotelRoom.class))).thenReturn(hotelRoom);
        when(hotelRoomPriceService.findById(anyLong())).thenReturn(hotelRoomPrice);
        assertEquals(hotelRoom, hotelRoomService.update(hotelRoom));
    }

    @Test
    public void testDelete() {
        final HotelRoom hotelRoom = new HotelRoom();
        hotelRoom.setId(1L);
        doNothing().when(hotelRoomRepository).delete(hotelRoom);
        assertDoesNotThrow(() -> hotelRoomService.delete(hotelRoom));
    }

    @Test
    public void testDeleteById() {
        doNothing().when(hotelRoomRepository).deleteById(anyLong());
        assertDoesNotThrow(() -> hotelRoomService.deleteById(1L));
    }
}