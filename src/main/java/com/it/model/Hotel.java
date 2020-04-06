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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Table Hotel representation
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Hotel")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "hotels_name", nullable = false)
    @NotNull(message = "{hotel.name.notNull}")
    @NotEmpty(message = "{hotel.name.notEmpty}")
    @Size(min = 3, max = 50, message = "{hotel.name.size}")
    private String hotelsName;

    @Column(nullable = false)
    @NotNull(message = "{hotel.stars.notNull}")
    private Integer stars;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "hotels_address_id")
    @NotNull(message = "{hotel.hotelsAddress.notNull}")
    private HotelsAddress hotelsAddress;

    @OneToMany(mappedBy = "hotel")
    private Set<HotelRoomHotel> hotelRoomHotels;

    public Hotel(Long id, String hotelsName, Integer stars) {
        this.id = id;
        this.hotelsName = hotelsName;
        this.stars = stars;
    }

    public Hotel(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", hotelsName='" + hotelsName + '\'' +
                ", stars=" + stars +
                ", hotelsAddress=" + hotelsAddress +
                '}';
    }
}
