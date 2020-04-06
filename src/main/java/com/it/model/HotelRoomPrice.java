package com.it.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

/**
 * Table Hotel_room_price representation
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Hotel_Room_Price")
public class HotelRoomPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "date_of_month", nullable = false)
    @NotNull(message = "{hotelRoomPrice.date.notNull}")
    private LocalDate date;

    @Column(name = "price_per_night", nullable = false)
    @NotNull(message = "{hotelRoomPrice.price.notNull}")
    private Double pricePerNight;

    @ManyToMany(mappedBy = "hotelRoomPrices", fetch = FetchType.LAZY)
    private Set<HotelRoom> hotelRooms;

    public HotelRoomPrice(Long id, LocalDate date, Double pricePerNight) {
        this.id = id;
        this.date = date;
        this.pricePerNight = pricePerNight;
    }

    public HotelRoomPrice(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "HotelRoomPrice{" +
                "id=" + id +
                ", date=" + date +
                ", pricePerNight=" + pricePerNight +
                '}';
    }
}
