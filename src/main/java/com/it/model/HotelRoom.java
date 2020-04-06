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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Table Hotel_Room representation
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Hotel_Room")
public class HotelRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "{hotelRoom.type.notNull}")
    @NotEmpty(message = "{hotelRoom.type.notEmpty}")
    @Size(min = 3, max = 50, message = "{hotelRoom.type.size}")
    private String type;

    @Column(name = "number_of_guests", nullable = false)
    @NotNull(message = "{hotelRoom.numberOfGuests.notNull}")
    private Integer numberOfGuests;

    @Column(name = "food_type", nullable = false)
    @NotNull(message = "{hotelRoom.foodType.notNull}")
    @NotEmpty(message = "{hotelRoom.foodType.notEmpty}")
    @Size(min = 3, max = 50, message = "{hotelRoom.foodType.size}")
    private String foodType;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "Hotel_room_Hotel_room_price",
            joinColumns = {@JoinColumn(name = "hotel_room_id")},
            inverseJoinColumns = {@JoinColumn(name = "hotel_room_price_id")})
    @NotNull(message = "{hotelRoom.hotelRoomPrices.notNull}")
    private Set<HotelRoomPrice> hotelRoomPrices;

    @OneToMany(mappedBy = "hotelRoom")
    private Set<HotelRoomHotel> hotelRoomHotels;

    public HotelRoom(Long id, String type, Integer numberOfGuests, String foodType, Set<HotelRoomPrice> hotelRoomPrices) {
        this.id = id;
        this.type = type;
        this.numberOfGuests = numberOfGuests;
        this.foodType = foodType;
        this.hotelRoomPrices = hotelRoomPrices;
    }

    public HotelRoom(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {

        return "HotelRoom{id=" + id + ", type='" + type + "', numberOfGuests=" + numberOfGuests +
                ", foodType='" + foodType + "', hotelRoomPrices=" +
                hotelRoomPrices.stream().map(HotelRoomPrice::getPricePerNight).collect(Collectors.toList()) + "}";
    }
}
