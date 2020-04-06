package com.it.controller;

import com.it.component.LocalizedMessageSource;
import com.it.dto.request.OrderListRequestDto;
import com.it.dto.response.OrderListResponseDto;
import com.it.model.*;
import com.it.service.OrderListService;
import lombok.RequiredArgsConstructor;
import org.dozer.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Controller for OrderList entity
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/orderLists")
public class OrderListController {

    private final Mapper mapper;
    private final LocalizedMessageSource localizedMessageSource;
    private final OrderListService orderListService;

    /**
     * Finds all OrderList entities
     *
     * @return - ResponseEntity with the given body and status code
     */
    @GetMapping
    public ResponseEntity<List<OrderListResponseDto>> getAll() {
        final List<OrderList> orderLists = orderListService.findAll();
        final List<OrderListResponseDto> orderListResponseDtos = orderLists.stream()
                .map(orderList -> {
                    OrderListResponseDto orderListResponseDto = mapper.map(orderList, OrderListResponseDto.class);
                    return setIds(orderList, orderListResponseDto);
                })
                .collect(Collectors.toList());
        return new ResponseEntity<>(orderListResponseDtos, HttpStatus.OK);
    }

    /**
     * Finds OrderList entity by id
     *
     * @param id - OrderList entity id
     * @return - ResponseEntity with the given body and status code
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderListResponseDto> getOne(@PathVariable Long id) {
        final OrderList orderList = orderListService.findById(id);
        final OrderListResponseDto orderListResponseDto = mapper.map(orderList, OrderListResponseDto.class);
        return new ResponseEntity<>(setIds(orderList, orderListResponseDto), HttpStatus.OK);
    }

    /**
     * Saves OrderList entity with transferred parameters
     *
     * @param orderListRequestDto - the body of the web request
     * @return - ResponseEntity with the given body and status code
     */
    @PostMapping
    public ResponseEntity<OrderListResponseDto> save(@Valid @RequestBody OrderListRequestDto orderListRequestDto) {
        final OrderList orderList = orderListService.save(getOrderList(orderListRequestDto));
        final OrderListResponseDto orderListResponseDto = mapper.map(orderList, OrderListResponseDto.class);
        return new ResponseEntity<>(setIds(orderList, orderListResponseDto), HttpStatus.OK);
    }

    /**
     * Updates OrderList entity with transferred parameters by entities id
     *
     * @param orderListRequestDto - the body of the web request
     * @param id                  - OrderList entity id
     * @return - ResponseEntity with the given body and status code
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<OrderListResponseDto> update(@Valid @RequestBody OrderListRequestDto orderListRequestDto, @PathVariable Long id) {
        if (!Objects.equals(id, orderListRequestDto.getId())) {
            throw new RuntimeException(localizedMessageSource.getMessage("controller.orderList.unexpectedId", new Object[]{}));
        }
        final OrderList orderList = orderListService.update(getOrderList(orderListRequestDto));
        final OrderListResponseDto orderListResponseDto = mapper.map(orderList, OrderListResponseDto.class);
        return new ResponseEntity<>(setIds(orderList, orderListResponseDto), HttpStatus.OK);
    }

    /**
     * Deletes OrderList entity by its id
     *
     * @param id - OrderList entity id
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        orderListService.deleteById(id);
    }

    /**
     * Creates new OrderList entity from the body of the web request.
     * Sets reference to a User, Insurance, Customer and HotelRoomHotel entities
     *
     * @param orderListRequestDto - the body of the web request
     * @return - new OrderList entity
     */
    private OrderList getOrderList(OrderListRequestDto orderListRequestDto) {
        final OrderList orderList = mapper.map(orderListRequestDto, OrderList.class);
        final User user = new User();
        final Insurance insurance = new Insurance();
        final Customer customer = new Customer();
        final HotelRoomHotel hotelRoomHotel = new HotelRoomHotel();
        user.setId(orderListRequestDto.getUserId());
        insurance.setId(orderListRequestDto.getInsuranceId());
        customer.setId(orderListRequestDto.getCustomerId());
        hotelRoomHotel.setId(orderListRequestDto.getHotelRoomHotelId());
        orderList.setUser(user);
        orderList.setInsurance(insurance);
        orderList.setCustomer(customer);
        orderList.setHotelRoomHotel(hotelRoomHotel);
        return orderList;
    }

    /**
     * Sets Ids of associated entities from OrderList entity to orderListResponseDto
     *
     * @param orderList            - OrderList entity
     * @param orderListResponseDto - OrderListResponseDto to which set Ids
     * @return - orderListResponseDto
     */
    private OrderListResponseDto setIds(OrderList orderList, OrderListResponseDto orderListResponseDto) {
        orderListResponseDto.setUserId(orderList.getUser().getId());
        orderListResponseDto.setCustomerId(orderList.getCustomer().getId());
        orderListResponseDto.setInsuranceId(orderList.getInsurance().getId());
        orderListResponseDto.setHotelRoomHotelId(orderList.getHotelRoomHotel().getId());
        return orderListResponseDto;
    }
}
