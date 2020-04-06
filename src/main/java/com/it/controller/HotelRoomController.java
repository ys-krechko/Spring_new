package com.it.controller;

import com.it.component.LocalizedMessageSource;
import com.it.dto.request.HotelRoomRequestDto;
import com.it.dto.response.HotelRoomResponseDto;
import com.it.model.HotelRoom;
import com.it.model.HotelRoomPrice;
import com.it.service.HotelRoomService;
import lombok.RequiredArgsConstructor;
import org.dozer.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Controller for HotelRoom entity
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/hotelRooms")
public class HotelRoomController {

    private final Mapper mapper;
    private final HotelRoomService hotelRoomService;
    private final LocalizedMessageSource localizedMessageSource;

    /**
     * Finds all HotelRoom entities
     *
     * @return - ResponseEntity with the given body and status code
     */
    @GetMapping
    public ResponseEntity<List<HotelRoomResponseDto>> getAll() {
        final List<HotelRoom> hotelRooms = hotelRoomService.findAll();
        final List<HotelRoomResponseDto> hotelRoomResponseDtoList = hotelRooms.stream()
                .map(hotelRoom -> mapper.map(hotelRoom, HotelRoomResponseDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(hotelRoomResponseDtoList, HttpStatus.OK);
    }

    /**
     * Finds HotelRoom entity by id
     *
     * @param id - HotelRoom entity id
     * @return - ResponseEntity with the given body and status code
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<HotelRoomResponseDto> getOne(@PathVariable Long id) {
        final HotelRoomResponseDto hotelRoomResponseDto = mapper.map(hotelRoomService.findById(id), HotelRoomResponseDto.class);
        return new ResponseEntity<>(hotelRoomResponseDto, HttpStatus.OK);
    }

    /**
     * Saves HotelRoom entity with transferred parameters
     *
     * @param hotelRoom - the body of the web request
     * @return - ResponseEntity with the given body and status code
     */
    @PostMapping
    public ResponseEntity<HotelRoomResponseDto> save(@Valid @RequestBody HotelRoomRequestDto hotelRoom) {
        final HotelRoomResponseDto hotelRoomResponseDto = mapper.map(hotelRoomService.save(getHotelRoom(hotelRoom)), HotelRoomResponseDto.class);
        return new ResponseEntity<>(hotelRoomResponseDto, HttpStatus.OK);
    }

    /**
     * Updates HotelRoom entity with transferred parameters by entities id
     *
     * @param hotelRoom - the body of the web request
     * @param id        - HotelRoom entity id
     * @return - ResponseEntity with the given body and status code
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<HotelRoomResponseDto> update(@Valid @RequestBody HotelRoomRequestDto hotelRoom, @PathVariable Long id) {
        if (!Objects.equals(id, hotelRoom.getId())) {
            throw new RuntimeException(localizedMessageSource.getMessage("controller.hotelRoom.unexpectedId", new Object[]{}));
        }
        final HotelRoomResponseDto hotelRoomResponseDto = mapper.map(hotelRoomService.update(getHotelRoom(hotelRoom)), HotelRoomResponseDto.class);
        return new ResponseEntity<>(hotelRoomResponseDto, HttpStatus.OK);
    }

    /**
     * Deletes HotelRoom entity by its id
     *
     * @param id - HotelRoom entity id
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        hotelRoomService.deleteById(id);
    }

    /**
     * Creates new HotelRoom entity from the body of the web request.
     * Sets reference to a Set of HotelRoomPrice entities
     *
     * @param hotelRoomRequestDto - the body of the web request
     * @return - new HotelRoom entity
     */
    private HotelRoom getHotelRoom(HotelRoomRequestDto hotelRoomRequestDto) {
        final HotelRoom hotelRoom = mapper.map(hotelRoomRequestDto, HotelRoom.class);
        Set<HotelRoomPrice> hotelRoomPrices = hotelRoomRequestDto.getHotelRoomPriceIds().stream()
                .map(hotelRoomPriceIds -> {
                    HotelRoomPrice hotelRoomPrice = new HotelRoomPrice();
                    hotelRoomPrice.setId(hotelRoomPriceIds);
                    return hotelRoomPrice;
                }).collect(Collectors.toSet());
        hotelRoom.setHotelRoomPrices(hotelRoomPrices);
        return hotelRoom;
    }
}
