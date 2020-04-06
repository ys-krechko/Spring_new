package com.it.repository;

import com.it.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for Hotel entity
 */
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    /**
     * Select all Hotel entities with joined HotelsAddress entities
     *
     * @return - List of Hotel entities
     */
    @Query("SELECT DISTINCT h FROM Hotel h JOIN FETCH h.hotelsAddress ORDER BY h.id")
    List<Hotel> findAllWithHotelsAddress();

    /**
     * Select Hotel entity by its ID with joined HotelsAddress entity
     *
     * @param id - Hotel entity's ID
     * @return - Hotel entity
     */
    @Query("SELECT DISTINCT h FROM Hotel h JOIN FETCH h.hotelsAddress WHERE h.id=:id ORDER BY h.id")
    Optional<Hotel> findByIdWithHotelsAddress(@Param("id") Long id);
}
