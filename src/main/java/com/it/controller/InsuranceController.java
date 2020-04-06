package com.it.controller;

import com.it.component.LocalizedMessageSource;
import com.it.dto.InsuranceDto;
import com.it.model.Insurance;
import com.it.service.InsuranceService;
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
 * Controller for Insurance entity
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/insurances")
public class InsuranceController {

    private final Mapper mapper;
    private final InsuranceService insuranceService;
    private final LocalizedMessageSource localizedMessageSource;

    /**
     * Finds all Insurance entities
     *
     * @return - ResponseEntity with the given body and status code
     */
    @GetMapping
    public ResponseEntity<List<InsuranceDto>> getAll() {
        final List<Insurance> insurances = insuranceService.findAll();
        final List<InsuranceDto> insuranceDtoList = insurances.stream()
                .map(insurance -> mapper.map(insurance, InsuranceDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(insuranceDtoList, HttpStatus.OK);
    }

    /**
     * Finds Insurance entity by id
     *
     * @param id - Insurance entity id
     * @return - ResponseEntity with the given body and status code
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<InsuranceDto> getOne(@PathVariable Long id) {
        final InsuranceDto insuranceDto = mapper.map(insuranceService.findById(id), InsuranceDto.class);
        return new ResponseEntity<>(insuranceDto, HttpStatus.OK);
    }

    /**
     * Saves Insurance entity with transferred parameters
     *
     * @param insurance - the body of the web request
     * @return - ResponseEntity with the given body and status code
     */
    @PostMapping
    public ResponseEntity<InsuranceDto> save(@Valid @RequestBody InsuranceDto insurance) {
        final InsuranceDto insuranceDto = mapper.map(insuranceService.save(mapper.map(insurance, Insurance.class)), InsuranceDto.class);
        return new ResponseEntity<>(insuranceDto, HttpStatus.OK);
    }

    /**
     * Updates Insurance entity with transferred parameters by entities id
     *
     * @param insurance - the body of the web request
     * @param id        - Insurance entity id
     * @return - ResponseEntity with the given body and status code
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<InsuranceDto> update(@Valid @RequestBody InsuranceDto insurance, @PathVariable Long id) {
        if (!Objects.equals(insurance.getId(), id)) {
            throw new RuntimeException(localizedMessageSource.getMessage("controller.insurance.unexpectedId", new Object[]{}));
        }
        final InsuranceDto insuranceDto = mapper.map(insuranceService.update(mapper.map(insurance, Insurance.class)), InsuranceDto.class);
        return new ResponseEntity<>(insuranceDto, HttpStatus.OK);
    }

    /**
     * Deletes Insurance entity by its id
     *
     * @param id - Insurance entity id
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        insuranceService.deleteById(id);
    }
}
