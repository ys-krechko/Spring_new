package com.it.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Table Hotel_Room_Hotels representation
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Hotel_Room_Hotel")
public class HotelRoomHotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    @NotNull(message = "{hotelRoomHotel.hotel.notNull}")
    private Hotel hotel;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_room_id")
    @NotNull(message = "{hotelRoomHotel.hotelRoom.notNull}")
    private HotelRoom hotelRoom;

    @OneToMany(mappedBy = "hotelRoomHotel")
    private Set<OrderList> orderList;

    public HotelRoomHotel(Long id, HotelRoom hotelRoom, Hotel hotel) {
        this.id = id;
        this.hotelRoom = hotelRoom;
        this.hotel = hotel;
    }

    public HotelRoomHotel(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "HotelRoomHotel{" +
                "id=" + id +
                ", hotel=" + hotel +
                ", hotelRoom=" + hotelRoom +
                '}';
    }
}
