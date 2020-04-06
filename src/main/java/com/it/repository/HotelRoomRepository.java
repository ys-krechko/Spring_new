package com.it.repository;

import com.it.model.HotelRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for HotelRoom entity
 */
public interface HotelRoomRepository extends JpaRepository<HotelRoom, Long> {

    /**
     * Finds List of price variables in HotelRoomPrice entity by HotelRoom entity's ID
     * and HotelRoomPrice's date
     *
     * @param id   - id of certain room in hotel
     * @param date - date in HotelRoomPrice entity
     * @return - List of HotelRoom's prices that match searching parameters
     */
    @Query("SELECT hrhrp.pricePerNight FROM HotelRoom hr JOIN hr.hotelRoomPrices hrhrp WHERE hr.id = :id and hrhrp.date = :date")
    Double findHotelRoomsPriceByHotelRoomsIdAndDate(@Param("id") Long id, @Param("date") LocalDate date);

    /**
     * Select all HotelRoom entities with joined HotelRoomPrice entities
     *
     * @return - List of HotelRoom entities
     */
    @Query("SELECT DISTINCT hr FROM HotelRoom hr JOIN FETCH hr.hotelRoomPrices ORDER BY hr.id")
    List<HotelRoom> findAllWithHotelRoomPrices();

    /**
     * Select HotelRoom entity by its ID with joined HotelRoomPrice entity
     *
     * @param id - HotelRoom entity's id
     * @return - HotelRoom entity
     */
    @Query("FROM HotelRoom hr JOIN FETCH hr.hotelRoomPrices WHERE hr.id=:id ORDER BY hr.id")
    Optional<HotelRoom> findByIdWithHotelRoomPrices(@Param("id") Long id);
}
