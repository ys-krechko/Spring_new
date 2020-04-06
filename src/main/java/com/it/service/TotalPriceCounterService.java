package com.it.service;

/**
 * Interface that contains methods for counting value of totalPrice
 * variable of OrderList entity
 */
public interface TotalPriceCounterService {

    /**
     * Count total price of tour, from hotel's room price per night and price of insurance
     *
     * @param orderId - Order's Id
     * @return - total price of tour
     */
    Double countTotalPrice(Long orderId);

    /**
     * Count price on base of days in month, in which tour began
     *
     * @param orderId - Order's Id
     */
    void countPriceFromDaysInCurrentMonth(Long orderId);

    /**
     * Count price on base of days in tour's next month after month in which tour began
     *
     * @param orderId - Orders Id
     */
    void countPriceFromDaysInNextMonth(Long orderId);

    /**
     * Finds List of HotelRoom entity's IDs in HotelRoomHotel entity by OrderList entity's ID
     *
     * @param orderId - OrderList's ID
     * @return - List of HotelRoom's IDs that match searching parameters
     */
    Long findHotelRoomIdFromHotelRoomHotelByOrderId(Long orderId);

    /**
     * Finds List of price variables in HotelRoomPrice entity by HotelRoom entity's ID
     * and HotelRoomPrice's date
     *
     * @param id    - id of certain room in hotel
     * @param year  - year of tour
     * @param month - month of tour
     * @return - List of HotelRoom's prices that match searching parameters
     */
    Double findHotelRoomPriceByDateAndHotelRoomId(Long id, int year, int month);

    /**
     * Finds price of insurance in Insurance entity by OrderList entity's ID
     *
     * @param orderId - OrderList's ID
     * @return - price of insurance that match searching parameters
     */
    Double findInsurancePriceByOrderId(Long orderId);

    /**
     * Finds number of tourists from OrderList entity by its ID
     *
     * @param orderId - OrderList's ID
     * @return - number of tourists
     */
    Integer findNumberOfTouristsFromOrderListById(Long orderId);
}
