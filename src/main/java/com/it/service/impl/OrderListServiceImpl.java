package com.it.service.impl;

import com.it.component.LocalizedMessageSource;
import com.it.config.utils.CacheName;
import com.it.model.OrderList;
import com.it.repository.OrderListRepository;
import com.it.service.CustomerService;
import com.it.service.HotelRoomHotelService;
import com.it.service.InsuranceService;
import com.it.service.OrderListService;
import com.it.service.TotalPriceCounterService;
import com.it.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of service interface for OrderList entity
 */
@CacheConfig(cacheNames = {CacheName.ORDER_LIST})
@RequiredArgsConstructor
@Service
public class OrderListServiceImpl implements OrderListService {

    private final LocalizedMessageSource localizedMessageSource;
    private final OrderListRepository orderListRepository;
    private final UserService userService;
    private final InsuranceService insuranceService;
    private final CustomerService customerService;
    private final HotelRoomHotelService hotelRoomHotelService;
    private final TotalPriceCounterService totalPriceCounterService;

    /**
     * Finds all OrderList entities
     *
     * @return - List of OrderList entities
     */
    @Override
    public List<OrderList> findAll() {
        return orderListRepository.findAll();
    }

    /**
     * Finds the OrderList entity with the given id
     *
     * @param id - OrderList entity id
     * @return - OrderList entity
     */
    @Cacheable(key = "#id")
    @Override
    public OrderList findById(Long id) {
        return orderListRepository.findById(id).orElseThrow(() -> new RuntimeException(localizedMessageSource.getMessage("error.orderList.notExist", new Object[]{})));
    }

    /**
     * Saves a given OrderList entity
     *
     * @param orderList - OrderList entity
     * @return - the saved OrderList entity
     */
    @CachePut(key = "#result.id")
    @Transactional
    @Override
    public OrderList save(OrderList orderList) {
        validate(orderList.getId() != null, localizedMessageSource.getMessage("error.orderList.notHaveId", new Object[]{}));
        return saveAndFlush(orderList);
    }

    /**
     * Updates a OrderList entity and flushes changes instantly
     *
     * @param orderList - OrderList entity
     * @return - the updated OrderList entity
     */
    @CachePut(key = "#result.id")
    @Transactional
    @Override
    public OrderList update(OrderList orderList) {
        final Long orderListId = orderList.getId();
        validate(orderListId == null, localizedMessageSource.getMessage("error.orderList.haveId", new Object[]{}));
        return saveAndFlush(orderList);
    }

    /**
     * Deletes a given OrderList entity
     *
     * @param orderList - OrderList entity
     */
    @CacheEvict(key = "#orderList.id")
    @Transactional
    @Override
    public void delete(OrderList orderList) {
        validate(orderList.getId() == null, localizedMessageSource.getMessage("error.orderList.haveId", new Object[]{}));
        orderListRepository.delete(orderList);
    }

    /**
     * Deletes the OrderList entity with the given id
     *
     * @param id - OrderList entity id
     */
    @CacheEvict(key = "#id")
    @Transactional
    @Override
    public void deleteById(Long id) {
        orderListRepository.deleteById(id);
    }

    /**
     * Updates a OrderList entity and flushes changes instantly.
     * Sets reference to a User, Insurance, Customer entities
     *
     * @param orderList - OrderList entity
     * @return - the saved OrderList entity
     */
    private OrderList saveAndFlush(OrderList orderList) {
        validate(orderList.getUser() == null || orderList.getUser().getId() == null, localizedMessageSource.getMessage("error.orderList.user.isNull", new Object[]{}));
        validate(orderList.getInsurance() == null || orderList.getInsurance().getId() == null, localizedMessageSource.getMessage("error.orderList.insurance.isNull", new Object[]{}));
        validate(orderList.getCustomer() == null || orderList.getCustomer().getId() == null, localizedMessageSource.getMessage("error.orderList.customer.isNull", new Object[]{}));
        validate(orderList.getHotelRoomHotel() == null || orderList.getHotelRoomHotel().getId() == null, localizedMessageSource.getMessage("error.orderList.hotelRoomHotel.isNull", new Object[]{}));
        orderList.setUser(userService.findById(orderList.getUser().getId()));
        orderList.setInsurance(insuranceService.findById(orderList.getInsurance().getId()));
        orderList.setCustomer(customerService.findById(orderList.getCustomer().getId()));
        orderList.setHotelRoomHotel(hotelRoomHotelService.findById(orderList.getHotelRoomHotel().getId()));
        orderList = orderListRepository.save(orderList);
        orderList.setTotalPrice(totalPriceCounterService.countTotalPrice(orderList.getId()));
        return orderListRepository.saveAndFlush(orderList);

    }

    /**
     * Checks, if passed parameter(s) matches the given condition
     *
     * @param expression   - condition for checking
     * @param errorMessage - message to be thrown in RuntimeException, if checking fails
     */
    private void validate(boolean expression, String errorMessage) {
        if (expression) {
            throw new RuntimeException(errorMessage);
        }
    }
}
