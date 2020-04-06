package com.it.controller;

import com.it.component.LocalizedMessageSource;
import com.it.dto.request.HotelRequestDto;
import com.it.dto.response.HotelResponseDto;
import com.it.model.Hotel;
import com.it.model.HotelsAddress;
import com.it.service.HotelService;
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
 * Controller for Hotel entity
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/hotels")
public class HotelController {

    private final HotelService hotelService;
    private final Mapper mapper;
    private final LocalizedMessageSource localizedMessageSource;

    /**
     * Finds all Hotel entities
     *
     * @return - ResponseEntity with the given body and status code
     */
    @GetMapping
    public ResponseEntity<List<HotelResponseDto>> getAll() {
        final List<Hotel> hotels = hotelService.findAll();
        final List<HotelResponseDto> hotelResponseDtoList = hotels.stream()
                .map(hotel -> mapper.map(hotel, HotelResponseDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(hotelResponseDtoList, HttpStatus.OK);
    }

    /**
     * Finds Hotel entity by id
     *
     * @param id - Hotel entity id
     * @return - ResponseEntity with the given body and status code
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<HotelResponseDto> getOne(@PathVariable Long id) {
        final HotelResponseDto hotelResponseDto = mapper.map(hotelService.findById(id), HotelResponseDto.class);
        return new ResponseEntity<>(hotelResponseDto, HttpStatus.OK);
    }

    /**
     * Saves Hotel entity with transferred parameters
     *
     * @param hotelRequestDto - the body of the web request
     * @return - ResponseEntity with the given body and status code
     */
    @PostMapping
    public ResponseEntity<HotelResponseDto> save(@Valid @RequestBody HotelRequestDto hotelRequestDto) {
        final HotelResponseDto hotelResponseDto = mapper.map(hotelService.save(getHotel(hotelRequestDto)), HotelResponseDto.class);
        return new ResponseEntity<>(hotelResponseDto, HttpStatus.OK);
    }

    /**
     * Updates Hotel entity with transferred parameters by entities id
     *
     * @param hotelRequestDto - the body of the web request
     * @param id              - Hotel entity id
     * @return - ResponseEntity with the given body and status code
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<HotelResponseDto> update(@Valid @RequestBody HotelRequestDto hotelRequestDto, @PathVariable Long id) {
        if (!Objects.equals(id, hotelRequestDto.getId())) {
            throw new RuntimeException(localizedMessageSource.getMessage("controller.hotel.unexpectedId", new Object[]{}));
        }
        final HotelResponseDto hotelResponseDto = mapper.map(hotelService.update(getHotel(hotelRequestDto)), HotelResponseDto.class);
        return new ResponseEntity<>(hotelResponseDto, HttpStatus.OK);
    }

    /**
     * Deletes Hotel entity by its id
     *
     * @param id - Hotel entity id
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        hotelService.deleteById(id);
    }

    /**
     * Creates new Hotel entity from the body of the web request.
     * Sets reference to the HotelsAddress entity.
     *
     * @param hotelRequestDto - the body of the web request
     * @return - new Hotel entity
     */
    private Hotel getHotel(HotelRequestDto hotelRequestDto) {
        final Hotel hotel = mapper.map(hotelRequestDto, Hotel.class);
        final HotelsAddress hotelsAddress = new HotelsAddress();
        hotelsAddress.setId(hotelRequestDto.getHotelsAddressId());
        hotel.setHotelsAddress(hotelsAddress);
        return hotel;
    }
}
