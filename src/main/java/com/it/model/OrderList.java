package com.it.model;

import lombok.Getter;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Table Order_List representation
 */
@Getter
@Setter
@Entity
@Table(name = "Order_List")
public class OrderList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "beginning_date_of_tour", nullable = false)
    @NotNull(message = "{orderList.beginningDateOfTour.notNull}")
    private LocalDate beginningDateOfTour;

    @Column(name = "amount_of_days_of_tour", nullable = false)
    @NotNull(message = "{orderList.amountOfDaysOfTour.notNull}")
    private Integer amountOfDaysOfTour;

    @Column(name = "number_of_tourists", nullable = false)
    @NotNull(message = "{orderList.numberOfTourists.notNull}")
    private Integer numberOfTourists;

    @Column(name = "total_price")
    private Double totalPrice;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "{orderList.user.notNull}")
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "insurance_id", nullable = false)
    @NotNull(message = "{orderList.insurance.notNull}")
    private Insurance insurance;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @NotNull(message = "{orderList.customer.notNull}")
    private Customer customer;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_room_hotel_id", nullable = false)
    @NotNull(message = "{orderList.hotelRoomHotel.notNull}")
    private HotelRoomHotel hotelRoomHotel;

    @Override
    public String toString() {

        return "OrderList{id=" + id + ", beginningDateOfTour=" + beginningDateOfTour + ", amountOfDaysOfTour=" +
                amountOfDaysOfTour + ", numberOfTourists=" + numberOfTourists + ", totalPrice=" + totalPrice +
                ", user=" + user.getId() + ", insurance=" + insurance.getId() + ", customer=" +
                customer.getId() + ", hotelRoomHotel=" + hotelRoomHotel.getId() + "}";
    }
}
