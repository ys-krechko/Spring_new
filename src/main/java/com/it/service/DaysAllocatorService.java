package com.it.service;

/**
 * Interface that contains methods for calculating amount of days of tour for count
 * tour's total price
 */
public interface DaysAllocatorService {

    /**
     * Allocates days of tour between months (if necessary) for counting total price of tour.
     *
     * @param orderId - OrderList's ID
     */
    void allocateDays(Long orderId);

    /**
     * Finds amountOfDaysOfTour variable in OrderList entity by entity's ID
     * and sets its value to local amountOfDaysOfTour variable
     *
     * @param orderId - OrderList's ID
     */
    void findAmountOfDaysOfTourFromOrder(Long orderId);

    /**
     * Finds beginningDateOfTour variable in OrderList entity by entity's ID
     * and sets its value to local date variable
     *
     * @param orderId - OrderList's ID
     */
    void findBeginningDateOfTourFromOrder(Long orderId);

    /**
     * Returns  value of daysInCurrentMonth variable
     *
     * @return - daysInCurrentMonth
     */
    Integer getDaysInCurrentMonth();

    /**
     * Returns  value of daysInNextMonth variable
     *
     * @return - daysInNextMonth
     */
    Integer getDaysInNextMonth();

    /**
     * Returns  value of monthOfToursBeginning variable
     *
     * @return - monthOfToursBeginning
     */
    Integer getMonthOfToursBeginning();

    /**
     * Returns  value of monthOfToursEnding variable
     *
     * @return - monthOfToursEnding
     */
    Integer getMonthOfToursEnding();

    /**
     * Returns  value of yearOfToursBeginning variable
     *
     * @return - yearOfToursBeginning
     */
    Integer getYearOfToursBeginning();

    /**
     * Returns  value of yearOfToursEnding variable
     *
     * @return - yearOfToursEnding
     */
    Integer getYearOfToursEnding();

}
