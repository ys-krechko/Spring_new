package com.it.repository;

import com.it.model.HotelRoomPrice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA Repository for HotelRoomPrice entity
 */
public interface HotelRoomPriceRepository extends JpaRepository<HotelRoomPrice, Long> {
}
