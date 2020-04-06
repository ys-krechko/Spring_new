package com.it.service.impl;

import com.it.repository.OrderListRepository;
import com.it.service.DaysAllocatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Implementation of interface DaysAllocatorService. Contains methods for calculating
 * amount of days of tour for count tour's total price
 */
@RequiredArgsConstructor
@Service
public class DaysAllocatorServiceImpl implements DaysAllocatorService {

    private final OrderListRepository orderListRepository;

    private LocalDate date;
    private Integer amountOfDaysOfTour;
    private Integer daysInCurrentMonth;
    private Integer daysInNextMonth;
    private Integer monthOfToursBeginning;
    private Integer monthOfToursEnding;
    private Integer yearOfToursBeginning;
    private Integer yearOfToursEnding;

    /**
     * Allocates days of tour between months (if necessary) for counting total price of tour.
     *
     * @param orderId - OrderList's ID
     */
    @Override
    public void allocateDays(Long orderId) {
        findBeginningDateOfTourFromOrder(orderId);
        findAmountOfDaysOfTourFromOrder(orderId);
        monthOfToursBeginning = date.getMonthValue();
        LocalDate newDate = date.plusDays(amountOfDaysOfTour - 1);
        monthOfToursEnding = newDate.getMonthValue();
        if (monthOfToursBeginning.equals(monthOfToursEnding)) {
            daysInCurrentMonth = amountOfDaysOfTour;
        } else {
            yearOfToursBeginning = date.getYear();
            yearOfToursEnding = newDate.getYear();
            LocalDate nextMonth = LocalDate.of(yearOfToursBeginning, monthOfToursBeginning, 1).plusMonths(1);
            int days = (int) ChronoUnit.DAYS.between(nextMonth, newDate.plusDays(1));
            daysInCurrentMonth = amountOfDaysOfTour - days;
            daysInNextMonth = amountOfDaysOfTour - daysInCurrentMonth;
        }
    }

    /**
     * Finds amountOfDaysOfTour variable in OrderList entity by entity's ID
     * and sets its value to local amountOfDaysOfTour variable
     *
     * @param orderId - OrderList's ID
     */
    @Override
    public void findAmountOfDaysOfTourFromOrder(Long orderId) {
        amountOfDaysOfTour = orderListRepository.findAmountOfDaysOfTourById(orderId);
    }

    /**
     * Finds beginningDateOfTour variable in OrderList entity by entity's ID
     * and sets its value to local date variable
     *
     * @param orderId - OrderList's ID
     */
    @Override
    public void findBeginningDateOfTourFromOrder(Long orderId) {
        date = orderListRepository.findBeginningDateOfTourById(orderId);
    }

    /**
     * Returns  value of daysInCurrentMonth variable
     *
     * @return - daysInCurrentMonth
     */
    @Override
    public Integer getDaysInCurrentMonth() {
        return daysInCurrentMonth;
    }

    /**
     * Returns  value of daysInNextMonth variable
     *
     * @return - daysInNextMonth
     */
    @Override
    public Integer getDaysInNextMonth() {
        return daysInNextMonth;
    }

    /**
     * Returns  value of monthOfToursBeginning variable
     *
     * @return - monthOfToursBeginning
     */
    @Override
    public Integer getMonthOfToursBeginning() {
        return monthOfToursBeginning;
    }

    /**
     * Returns  value of monthOfToursEnding variable
     *
     * @return - monthOfToursEnding
     */
    @Override
    public Integer getMonthOfToursEnding() {
        return monthOfToursEnding;
    }

    /**
     * Returns  value of yearOfToursBeginning variable
     *
     * @return - yearOfToursBeginning
     */
    @Override
    public Integer getYearOfToursBeginning() {
        return yearOfToursBeginning;
    }

    /**
     * Returns  value of yearOfToursEnding variable
     *
     * @return - yearOfToursEnding
     */
    @Override
    public Integer getYearOfToursEnding() {
        return yearOfToursEnding;
    }
}
