package com.it.controller;

import com.it.component.LocalizedMessageSource;
import com.it.dto.CustomerDto;
import com.it.model.Customer;
import com.it.service.CustomerService;
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
@RequestMapping("/customers")
public class CustomerController {

    private final Mapper mapper;
    private final CustomerService customerService;
    private final LocalizedMessageSource localizedMessageSource;

    /**
     * Finds all Customer entities
     *
     * @return - ResponseEntity with the given body and status code
     */
    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAll() {
        final List<Customer> insurances = customerService.findAll();
        final List<CustomerDto> customerDtoList = insurances.stream()
                .map(insurance -> mapper.map(insurance, CustomerDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(customerDtoList, HttpStatus.OK);
    }

    /**
     * Finds Customer entity by id
     *
     * @param id - Customer entity id
     * @return - ResponseEntity with the given body and status code
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomerDto> getOne(@PathVariable Long id) {
        final CustomerDto customerDto = mapper.map(customerService.findById(id), CustomerDto.class);
        return new ResponseEntity<>(customerDto, HttpStatus.OK);
    }

    /**
     * Saves Customer entity with transferred parameters
     *
     * @param customer - the body of the web request
     * @return - ResponseEntity with the given body and status code
     */
    @PostMapping
    public ResponseEntity<CustomerDto> save(@Valid @RequestBody CustomerDto customer) {
        final CustomerDto customerDto = mapper.map(customerService.save(mapper.map(customer, Customer.class)), CustomerDto.class);
        return new ResponseEntity<>(customerDto, HttpStatus.OK);
    }

    /**
     * Updates Customer entity with transferred parameters by entities id
     *
     * @param customer - the body of the web request
     * @param id       - Customer entity id
     * @return - ResponseEntity with the given body and status code
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<CustomerDto> update(@Valid @RequestBody CustomerDto customer, @PathVariable Long id) {
        if (!Objects.equals(customer.getId(), id)) {
            throw new RuntimeException(localizedMessageSource.getMessage("controller.customer.unexpectedId", new Object[]{}));
        }
        final CustomerDto customerDto = mapper.map(customerService.update(mapper.map(customer, Customer.class)), CustomerDto.class);
        return new ResponseEntity<>(customerDto, HttpStatus.OK);
    }

    /**
     * Deletes Customer entity by its id
     *
     * @param id - Customer entity id
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        customerService.deleteById(id);
    }
}
