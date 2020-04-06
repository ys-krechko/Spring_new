package com.it.service;

import com.it.model.OrderList;

import java.util.List;

/**
 * Interface with CRUD methods for OrderList entity
 */
public interface OrderListService {

    /**
     * Finds all OrderList entities
     *
     * @return - List of OrderList entities
     */
    List<OrderList> findAll();

    /**
     * Finds the OrderList entity with the given id
     *
     * @param id - OrderList entity id
     * @return - OrderList entity
     */
    OrderList findById(Long id);

    /**
     * Saves a given OrderList entity
     *
     * @param orderList - OrderList entity
     * @return - the saved OrderList entity
     */
    OrderList save(OrderList orderList);

    /**
     * Updates a OrderList entity and flushes changes instantly
     *
     * @param orderList - OrderList entity
     * @return - the updated OrderList entity
     */
    OrderList update(OrderList orderList);

    /**
     * Deletes a given OrderList entity
     *
     * @param orderList - OrderList entity
     */
    void delete(OrderList orderList);

    /**
     * Deletes the OrderList entity with the given id
     *
     * @param id - OrderList entity id
     */
    void deleteById(Long id);
}
