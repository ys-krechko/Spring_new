package com.it.controller;

import com.it.component.LocalizedMessageSource;
import com.it.dto.RoleDto;
import com.it.model.Role;
import com.it.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.dozer.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Controller for Role entity
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/roles")
public class RoleController {

    private final Mapper mapper;
    private final RoleService rolesService;
    private final LocalizedMessageSource localizedMessageSource;

    /**
     * Finds all Role entities
     *
     * @return - ResponseEntity with the given body and status code
     */
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<RoleDto>> getAll() {
        final List<Role> roles = rolesService.findAll();
        final List<RoleDto> roleDtoList = roles.stream()
                .map(role -> mapper.map(role, RoleDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(roleDtoList, HttpStatus.OK);
    }

    /**
     * Finds Role entity by id
     *
     * @param id - Role entity's id
     * @return - ResponseEntity with the given body and status code
     */
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<RoleDto> getOne(@PathVariable Long id) {
        final RoleDto roleDto = mapper.map(rolesService.findById(id), RoleDto.class);
        return new ResponseEntity<>(roleDto, HttpStatus.OK);
    }

    /**
     * Saves Role entity with transferred parameters
     *
     * @param role - the body of the web request
     * @return - ResponseEntity with the given body and status code
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<RoleDto> save(@Valid @RequestBody RoleDto role) {
        final RoleDto roleDto = mapper.map(rolesService.save(mapper.map(role, Role.class)), RoleDto.class);
        return new ResponseEntity<>(roleDto, HttpStatus.OK);
    }

    /**
     * Updates Role entity with transferred parameters by entities id
     *
     * @param role - the body of the web request
     * @param id   - Role entity's id
     * @return - ResponseEntity with the given body and status code
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<RoleDto> update(@Valid @RequestBody RoleDto role, @PathVariable Long id) {
        if (!Objects.equals(id, role.getId())) {
            throw new RuntimeException(localizedMessageSource.getMessage("controller.role.unexpectedId", new Object[]{}));
        }
        final RoleDto roleDto = mapper.map(rolesService.update(mapper.map(role, Role.class)), RoleDto.class);
        return new ResponseEntity<>(roleDto, HttpStatus.OK);
    }

    /**
     * Deletes Role entity by its id
     *
     * @param id - Role entity's id
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        rolesService.deleteById(id);
    }
}
