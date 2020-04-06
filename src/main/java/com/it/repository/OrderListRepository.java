package com.it.repository;

import com.it.model.OrderList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

/**
 * JPA Repository for OrderList entity
 */
public interface OrderListRepository extends JpaRepository<OrderList, Long> {

    /**
     * Finds beginningDateOfTour variable in OrderList entity by entity's ID
     *
     * @param id - OrderList's ID
     * @return - beginningDateOfTour variable that match searching parameters
     */
    @Query("SELECT orl.beginningDateOfTour FROM OrderList orl WHERE orl.id = :id")
    LocalDate findBeginningDateOfTourById(@Param("id") Long id);

    /**
     * Finds amountOfDaysOfTour variable in OrderList entity by entity's ID
     *
     * @param id - OrderList's ID
     * @return - amountOfDaysOfTour variable that match searching parameters
     */
    @Query("SELECT orl.amountOfDaysOfTour FROM OrderList orl WHERE orl.id = :id")
    Integer findAmountOfDaysOfTourById(@Param("id") Long id);

    /**
     * Finds price of insurance in Insurance entity by OrderList entity's ID
     *
     * @param id - OrderList's ID
     * @return - price of insurance that match searching parameters
     */
    @Query("SELECT oli.insurancePrice FROM OrderList ol JOIN ol.insurance oli WHERE ol.id=:id")
    Double findInsurancePriceByOrderListId(@Param("id") Long id);

    /**
     * Finds List of HotelRoom entity's IDs in HotelRoomHotel entity by OrderList entity's ID
     *
     * @param id - OrderList's ID
     * @return - List of HotelRoom's IDs that match searching parameters
     */
    @Query("SELECT olhrhhr.id FROM OrderList ol JOIN ol.hotelRoomHotel.hotelRoom olhrhhr WHERE ol.id=:id")
    Long findHotelRoomIdByOrderListId(@Param("id") Long id);

    /**
     * Finds numberOfTourists variable in OrderList entity by entity's ID
     *
     * @param id - OrderList's ID
     * @return - numberOfTourists variable that match searching parameters
     */
    @Query("SELECT ol.numberOfTourists FROM OrderList ol WHERE ol.id=:id")
    Integer findNumberOfTourists(@Param("id") Long id);
}
