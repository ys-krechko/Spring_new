package com.it.service.impl;

import com.it.repository.HotelRoomRepository;
import com.it.repository.OrderListRepository;
import com.it.service.DaysAllocatorService;
import com.it.service.TotalPriceCounterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


/**
 * Implementation of interface TotalPriceCounterService. Contains methods for counting
 * value of totalPrice variable of OrderList entity
 */
@RequiredArgsConstructor
@Service
public class TotalPriceCounterServiceImpl implements TotalPriceCounterService {

    private final HotelRoomRepository hotelRoomRepository;
    private final OrderListRepository orderListRepository;
    private final DaysAllocatorService daysAllocatorService;

    private Double totalPrice = 0.00;

    /**
     * Count total price of tour, from hotel's room price per night and price of insurance
     *
     * @param orderId - Order's Id
     * @return - total price of tour
     */
    @Override
    public Double countTotalPrice(Long orderId) {
        daysAllocatorService.allocateDays(orderId);
        if (daysAllocatorService.getDaysInNextMonth() == 0) {
            countPriceFromDaysInCurrentMonth(orderId);
        } else {
            countPriceFromDaysInCurrentMonth(orderId);
            countPriceFromDaysInNextMonth(orderId);
        }
        totalPrice += (findInsurancePriceByOrderId(orderId) * findNumberOfTouristsFromOrderListById(orderId));
        return totalPrice;
    }

    /**
     * Count price on base of days in month, in which tour began
     *
     * @param orderId - Order's Id
     */
    @Override
    public void countPriceFromDaysInCurrentMonth(Long orderId) {
        Long hotelRoomId = findHotelRoomIdFromHotelRoomHotelByOrderId(orderId);
        Double hotelRoomPrice = findHotelRoomPriceByDateAndHotelRoomId(hotelRoomId, daysAllocatorService.getYearOfToursBeginning(), daysAllocatorService.getMonthOfToursBeginning());
        totalPrice = (hotelRoomPrice * daysAllocatorService.getDaysInCurrentMonth());
    }

    /**
     * Count price on base of days in tour's next month after month in which tour began
     *
     * @param orderId - Orders Id
     */
    @Override
    public void countPriceFromDaysInNextMonth(Long orderId) {
        Long hotelRoomId = findHotelRoomIdFromHotelRoomHotelByOrderId(orderId);
        Double hotelRoomPrice = findHotelRoomPriceByDateAndHotelRoomId(hotelRoomId, daysAllocatorService.getYearOfToursEnding(), daysAllocatorService.getMonthOfToursEnding());
        totalPrice += (hotelRoomPrice * daysAllocatorService.getDaysInNextMonth());
    }

    /**
     * Finds List of HotelRoom entity's IDs in HotelRoomHotel entity by OrderList entity's ID
     *
     * @param orderId - OrderList's ID
     * @return - List of HotelRoom's IDs that match searching parameters
     */
    @Override
    public Long findHotelRoomIdFromHotelRoomHotelByOrderId(Long orderId) {
        return orderListRepository.findHotelRoomIdByOrderListId(orderId);
    }

    /**
     * Finds List of price variables in HotelRoomPrice entity by HotelRoom entity's ID
     * and HotelRoomPrice's date
     *
     * @param id    - id of certain room in hotel
     * @param year  - year of tour
     * @param month - month of tour
     * @return - List of HotelRoom's prices that match searching parameters
     */
    @Override
    public Double findHotelRoomPriceByDateAndHotelRoomId(Long id, int year, int month) {
        return hotelRoomRepository.findHotelRoomsPriceByHotelRoomsIdAndDate(id, LocalDate.of(year, month, 1));
    }

    /**
     * Finds price of insurance in Insurance entity by OrderList entity's ID
     *
     * @param orderId - OrderList's ID
     * @return - price of insurance that match searching parameters
     */
    @Override
    public Double findInsurancePriceByOrderId(Long orderId) {
        return orderListRepository.findInsurancePriceByOrderListId(orderId);
    }

    /**
     * Finds number of tourists from OrderList entity by its ID
     *
     * @param orderId - OrderList's ID
     * @return - number of tourists
     */
    @Override
    public Integer findNumberOfTouristsFromOrderListById(Long orderId) {
        return orderListRepository.findNumberOfTourists(orderId);
    }
}
