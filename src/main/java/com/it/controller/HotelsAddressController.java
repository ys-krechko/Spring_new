package com.it.controller;

import com.it.component.LocalizedMessageSource;
import com.it.dto.HotelsAddressDto;
import com.it.model.HotelsAddress;
import com.it.service.HotelsAddressService;
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
 * Controller for HotelsAddress entity
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/hotelsAddresses")
public class HotelsAddressController {

    private final Mapper mapper;
    private final HotelsAddressService hotelsAddressService;
    private final LocalizedMessageSource localizedMessageSource;

    /**
     * Finds all HotelsAddress entities
     *
     * @return - ResponseEntity with the given body and status code
     */
    @GetMapping
    public ResponseEntity<List<HotelsAddressDto>> getAll() {
        final List<HotelsAddress> roles = hotelsAddressService.findAll();
        final List<HotelsAddressDto> hotelsAddressDtos = roles.stream()
                .map(role -> mapper.map(role, HotelsAddressDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(hotelsAddressDtos, HttpStatus.OK);
    }

    /**
     * Finds HotelsAddress entity by id
     *
     * @param id - HotelsAddress entity's id
     * @return - ResponseEntity with the given body and status code
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<HotelsAddressDto> getOne(@PathVariable Long id) {
        final HotelsAddressDto hotelsAddressDto = mapper.map(hotelsAddressService.findById(id), HotelsAddressDto.class);
        return new ResponseEntity<>(hotelsAddressDto, HttpStatus.OK);
    }

    /**
     * Saves HotelsAddress entity with transferred parameters
     *
     * @param hotelsAddress - the body of the web request
     * @return - ResponseEntity with the given body and status code
     */
    @PostMapping
    public ResponseEntity<HotelsAddressDto> save(@Valid @RequestBody HotelsAddressDto hotelsAddress) {
        final HotelsAddressDto hotelsAddressDto = mapper.map(hotelsAddressService.save(mapper.map(hotelsAddress, HotelsAddress.class)), HotelsAddressDto.class);
        return new ResponseEntity<>(hotelsAddressDto, HttpStatus.OK);
    }

    /**
     * Updates HotelsAddress entity with transferred parameters by entities id
     *
     * @param hotelsAddress - the body of the web request
     * @param id            - HotelsAddress entity's id
     * @return - ResponseEntity with the given body and status code
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<HotelsAddressDto> update(@Valid @RequestBody HotelsAddressDto hotelsAddress, @PathVariable Long id) {
        if (!Objects.equals(id, hotelsAddress.getId())) {
            throw new RuntimeException(localizedMessageSource.getMessage("controller.hotelsAddress.unexpectedId", new Object[]{}));
        }
        final HotelsAddressDto hotelsAddressDto = mapper.map(hotelsAddressService.update(mapper.map(hotelsAddress, HotelsAddress.class)), HotelsAddressDto.class);
        return new ResponseEntity<>(hotelsAddressDto, HttpStatus.OK);
    }

    /**
     * Deletes HotelsAddress entity by its id
     *
     * @param id - HotelsAddress entity's id
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        hotelsAddressService.deleteById(id);
    }
}
