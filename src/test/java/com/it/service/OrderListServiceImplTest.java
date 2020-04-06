package com.it.service;

import com.it.component.LocalizedMessageSource;
import com.it.model.*;
import com.it.repository.OrderListRepository;
import com.it.service.impl.OrderListServiceImpl;
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
 * Contains tests for OrderListServiceImpl class
 */
@ExtendWith(MockitoExtension.class)
public class OrderListServiceImplTest {

    @InjectMocks
    private OrderListServiceImpl orderListService;

    @Mock
    private OrderListRepository orderListRepository;

    @Mock
    private UserService userService;

    @Mock
    private InsuranceService insuranceService;

    @Mock
    private CustomerService customerService;

    @Mock
    private HotelRoomHotelService hotelRoomHotelService;

    @Mock
    private TotalPriceCounterService totalPriceCounterService;

    @Mock
    private LocalizedMessageSource localizedMessageSource;

    @Test
    public void testFindAll() {
        final List<OrderList> orderLists = Collections.singletonList(new OrderList());
        when(orderListRepository.findAll()).thenReturn(orderLists);
        assertEquals(orderLists, orderListService.findAll());
    }

    @Test
    public void testFindById() {
        final OrderList orderList = new OrderList();
        when(orderListRepository.findById(anyLong())).thenReturn(Optional.of(orderList));
        assertEquals(orderList, orderListService.findById(1L));
    }

    @Test
    public void testSave() {
        final OrderList orderList = new OrderList();
        final OrderList orderList1 = new OrderList();
        orderList1.setId(1L);
        final User user = new User(1L);
        final Insurance insurance = new Insurance(1L);
        final Customer customer = new Customer(1L);
        final HotelRoomHotel hotelRoomHotel = new HotelRoomHotel(1L);
        orderList.setUser(user);
        orderList.setInsurance(insurance);
        orderList.setCustomer(customer);
        orderList.setHotelRoomHotel(hotelRoomHotel);
        when(orderListRepository.save(any(OrderList.class))).thenReturn(orderList1);
        when(orderListRepository.saveAndFlush(any(OrderList.class))).thenReturn(orderList1);
        when(userService.findById(anyLong())).thenReturn(user);
        when(insuranceService.findById(anyLong())).thenReturn(insurance);
        when(customerService.findById(anyLong())).thenReturn(customer);
        when(hotelRoomHotelService.findById(anyLong())).thenReturn(hotelRoomHotel);
        when(totalPriceCounterService.countTotalPrice(any(Long.class))).thenReturn(2.50);
        assertEquals(orderList1, orderListService.save(orderList));
    }

    @Test
    public void testUpdate() {
        final OrderList orderList = new OrderList();
        orderList.setId(1L);
        final User user = new User(1L);
        final Insurance insurance = new Insurance(1L);
        final Customer customer = new Customer(1L);
        final HotelRoomHotel hotelRoomHotel = new HotelRoomHotel(1L);
        orderList.setUser(user);
        orderList.setInsurance(insurance);
        orderList.setCustomer(customer);
        orderList.setHotelRoomHotel(hotelRoomHotel);
        when(orderListRepository.save(any(OrderList.class))).thenReturn(orderList);
        when(orderListRepository.saveAndFlush(any(OrderList.class))).thenReturn(orderList);
        when(userService.findById(anyLong())).thenReturn(user);
        when(insuranceService.findById(anyLong())).thenReturn(insurance);
        when(customerService.findById(anyLong())).thenReturn(customer);
        when(hotelRoomHotelService.findById(anyLong())).thenReturn(hotelRoomHotel);
        when(totalPriceCounterService.countTotalPrice(anyLong())).thenReturn(2.50);
        assertEquals(orderList, orderListService.update(orderList));
    }

    @Test
    public void testDelete() {
        final OrderList orderList = new OrderList();
        orderList.setId(1L);
        doNothing().when(orderListRepository).delete(orderList);
        assertDoesNotThrow(() -> orderListService.delete(orderList));
    }

    @Test
    public void testDeleteById() {
        doNothing().when(orderListRepository).deleteById(anyLong());
        assertDoesNotThrow(() -> orderListService.deleteById(1L));
    }
}