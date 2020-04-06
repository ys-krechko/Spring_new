package com.it.controller;

import com.it.component.LocalizedMessageSource;
import com.it.dto.request.HotelRoomHotelRequestDTO;
import com.it.dto.response.HotelRoomHotelResponseDTO;
import com.it.model.Hotel;
import com.it.model.HotelRoom;
import com.it.model.HotelRoomHotel;
import com.it.service.HotelRoomHotelService;
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
 * Controller for HotelRoomHotel entity
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/hotelRoomHotels")
public class HotelRoomHotelController {

    private final Mapper mapper;
    private final HotelRoomHotelService hotelRoomHotelService;
    private final LocalizedMessageSource localizedMessageSource;

    /**
     * Finds all HotelRoomHotel entities
     *
     * @return - ResponseEntity with the given body and status code
     */
    @GetMapping
    public ResponseEntity<List<HotelRoomHotelResponseDTO>> getAll() {
        final List<HotelRoomHotel> hotelRoomHotels = hotelRoomHotelService.findAll();
        final List<HotelRoomHotelResponseDTO> hotelRoomHotelResponseDTOList = hotelRoomHotels.stream()
                .map(hotelRoomHotel -> mapper.map(hotelRoomHotel, HotelRoomHotelResponseDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(hotelRoomHotelResponseDTOList, HttpStatus.OK);
    }

    /**
     * Finds HotelRoomHotel entity by id
     *
     * @param id - HotelRoomHotel entity id
     * @return - ResponseEntity with the given body and status code
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<HotelRoomHotelResponseDTO> getOne(@PathVariable Long id) {
        final HotelRoomHotelResponseDTO hotelRoomHotelResponseDTO = mapper.map(hotelRoomHotelService.findById(id), HotelRoomHotelResponseDTO.class);
        return new ResponseEntity<>(hotelRoomHotelResponseDTO, HttpStatus.OK);
    }

    /**
     * Saves HotelRoomHotel entity with transferred parameters
     *
     * @param hotelRoomHotelRequestDTO - the body of the web request
     * @return - ResponseEntity with the given body and status code
     */
    @PostMapping
    public ResponseEntity<HotelRoomHotelResponseDTO> save(@Valid @RequestBody HotelRoomHotelRequestDTO hotelRoomHotelRequestDTO) {
        final HotelRoomHotelResponseDTO hotelRoomHotelResponseDTO = mapper.map(hotelRoomHotelService.save(getHotelRoomHotel(hotelRoomHotelRequestDTO)), HotelRoomHotelResponseDTO.class);
        return new ResponseEntity<>(hotelRoomHotelResponseDTO, HttpStatus.OK);
    }

    /**
     * Updates HotelRoomHotel entity with transferred parameters by entities id
     *
     * @param hotelRoomHotel - the body of the web request
     * @param id             - HotelRoomHotel entity id
     * @return - ResponseEntity with the given body and status code
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<HotelRoomHotelResponseDTO> update(@Valid @RequestBody HotelRoomHotelRequestDTO hotelRoomHotel, @PathVariable Long id) {
        if (!Objects.equals(id, hotelRoomHotel.getId())) {
            throw new RuntimeException(localizedMessageSource.getMessage("controller.hotelRoomHotel.unexpectedId", new Object[]{}));
        }
        final HotelRoomHotelResponseDTO hotelRoomHotelResponseDTO = mapper.map(hotelRoomHotelService.update(getHotelRoomHotel(hotelRoomHotel)), HotelRoomHotelResponseDTO.class);
        return new ResponseEntity<>(hotelRoomHotelResponseDTO, HttpStatus.OK);
    }

    /**
     * Deletes HotelRoomHotel entity by its id
     *
     * @param id - HotelRoomHotel entity id
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        hotelRoomHotelService.deleteById(id);
    }

    /**
     * Creates new HotelRoomHotel entity from the body of the web request.
     * Sets reference to the Hotel and HotelRoom entities
     *
     * @param hotelRoomHotelRequestDTO - the body of the web request
     * @return - new HotelRoomHotel entity
     */
    private HotelRoomHotel getHotelRoomHotel(HotelRoomHotelRequestDTO hotelRoomHotelRequestDTO) {
        final HotelRoomHotel hotelRoomHotel = mapper.map(hotelRoomHotelRequestDTO, HotelRoomHotel.class);
        final Hotel hotel = new Hotel();
        final HotelRoom hotelRoom = new HotelRoom();
        hotel.setId(hotelRoomHotelRequestDTO.getHotelId());
        hotelRoom.setId(hotelRoomHotelRequestDTO.getHotelRoomId());
        hotelRoomHotel.setHotel(hotel);
        hotelRoomHotel.setHotelRoom(hotelRoom);
        return hotelRoomHotel;
    }
}
