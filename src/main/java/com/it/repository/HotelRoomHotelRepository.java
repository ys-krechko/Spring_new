package com.it.repository;

import com.it.model.HotelRoomHotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for HotelRoomHotel entity
 */
public interface HotelRoomHotelRepository extends JpaRepository<HotelRoomHotel, Long> {

    /**
     * Select all HotelRoomHotel entities with joined HotelRoom and Hotel entities
     *
     * @return - List of HotelRoomHotel entities
     */
    @Query("SELECT DISTINCT hrh FROM HotelRoomHotel hrh JOIN FETCH hrh.hotelRoom hrhhr JOIN FETCH hrhhr.hotelRoomPrices JOIN FETCH hrh.hotel hrhh JOIN FETCH hrhh.hotelsAddress ORDER BY hrh.id")
    List<HotelRoomHotel> findAllWithHotelAndAndHotelRoom();

    /**
     * Select HotelRoomHotel entity by its ID with joined HotelRoom and Hotel entities
     *
     * @param id - HotelRoomHotel entity's ID
     * @return - HotelRoomHotel entity
     */
    @Query("SELECT DISTINCT hrh FROM HotelRoomHotel hrh JOIN FETCH hrh.hotelRoom hrhhr JOIN FETCH hrhhr.hotelRoomPrices JOIN FETCH hrh.hotel hrhh JOIN FETCH hrhh.hotelsAddress WHERE hrh.id=:id ORDER BY hrh.id")
    Optional<HotelRoomHotel> findByIdWithHotelAndAndHotelRoom(@Param("id") Long id);
}
