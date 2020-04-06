package com.it.controller;

import com.it.component.LocalizedMessageSource;
import com.it.dto.HotelRoomPriceDto;
import com.it.model.HotelRoomPrice;
import com.it.service.HotelRoomPriceService;
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
 * Controller for HotelRoomPrice entity
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/hotelRoomPrices")
public class HotelRoomPriceController {

    private final Mapper mapper;
    private final HotelRoomPriceService hotelRoomPriceService;
    private final LocalizedMessageSource localizedMessageSource;

    /**
     * Finds all HotelRoomPrice entities
     *
     * @return - ResponseEntity with the given body and status code
     */
    @GetMapping
    public ResponseEntity<List<HotelRoomPriceDto>> getAll() {
        final List<HotelRoomPrice> hotelRoomPrices = hotelRoomPriceService.findAll();
        final List<HotelRoomPriceDto> hotelRoomPriceDtoList = hotelRoomPrices.stream()
                .map(hotelRoomPrice -> mapper.map(hotelRoomPrice, HotelRoomPriceDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(hotelRoomPriceDtoList, HttpStatus.OK);
    }

    /**
     * Finds HotelRoomPrice entity by id
     *
     * @param id - HotelRoomPrice entity id
     * @return - ResponseEntity with the given body and status code
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<HotelRoomPriceDto> getOne(@PathVariable Long id) {
        final HotelRoomPriceDto hotelRoomPriceDto = mapper.map(hotelRoomPriceService.findById(id), HotelRoomPriceDto.class);
        return new ResponseEntity<>(hotelRoomPriceDto, HttpStatus.OK);
    }

    /**
     * Saves HotelRoomPrice entity with transferred parameters
     *
     * @param hotelRoomPrice - the body of the web request
     * @return - ResponseEntity with the given body and status code
     */
    @PostMapping
    public ResponseEntity<HotelRoomPriceDto> save(@Valid @RequestBody HotelRoomPriceDto hotelRoomPrice) {
        final HotelRoomPriceDto hotelRoomPriceDto = mapper.map(hotelRoomPriceService.save(mapper.map(hotelRoomPrice, HotelRoomPrice.class)), HotelRoomPriceDto.class);
        return new ResponseEntity<>(hotelRoomPriceDto, HttpStatus.OK);
    }

    /**
     * Updates HotelRoomPrice entity with transferred parameters by entities id
     *
     * @param hotelRoomPrice - the body of the web request
     * @param id             - HotelRoomPrice entity id
     * @return - ResponseEntity with the given body and status code
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<HotelRoomPriceDto> update(@Valid @RequestBody HotelRoomPriceDto hotelRoomPrice, @PathVariable Long id) {
        if (!Objects.equals(id, hotelRoomPrice.getId())) {
            throw new RuntimeException(localizedMessageSource.getMessage("controller.hotelRoomPrices.unexpectedId", new Object[]{}));
        }
        final HotelRoomPriceDto hotelRoomPriceDto = mapper.map(hotelRoomPriceService.update(mapper.map(hotelRoomPrice, HotelRoomPrice.class)), HotelRoomPriceDto.class);
        return new ResponseEntity<>(hotelRoomPriceDto, HttpStatus.OK);
    }

    /**
     * Deletes HotelRoomPrice entity by its id
     *
     * @param id - HotelRoomPrice entity id
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        hotelRoomPriceService.deleteById(id);
    }
}
