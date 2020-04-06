package com.it.controller;

import com.it.component.LocalizedMessageSource;
import com.it.dto.request.UserRequestDto;
import com.it.dto.response.UserResponseDto;
import com.it.model.Role;
import com.it.model.User;
import com.it.service.UserService;
import lombok.RequiredArgsConstructor;
import org.dozer.Mapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Controller for User entity
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final Mapper mapper;
    private final UserService userService;
    private final LocalizedMessageSource localizedMessageSource;

    /**
     * Finds all User entities
     *
     * @return - ResponseEntity with the given body and status code
     */
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll() {
        final List<User> users = userService.findAll();
        final List<UserResponseDto> userResponseDtoList = users.stream()
                .map(user -> mapper.map(user, UserResponseDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(userResponseDtoList, HttpStatus.OK);
    }

    /**
     * Finds User entity by id
     *
     * @param id - User entity id
     * @return - ResponseEntity with the given body and status code
     */
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponseDto> getOne(@PathVariable Long id) {
        final UserResponseDto userResponseDto = mapper.map(userService.findById(id), UserResponseDto.class);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    /**
     * Redirects request to signUp method of AuthenticationController
     *
     * @return - ResponseEntity which redirects request to signUp method of AuthenticationController
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Void> save() {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(userService.getRedirectUrl()));
        return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT).headers(headers).build();
    }

    /**
     * Updates User entity with transferred parameters by entities id
     *
     * @param user - the body of the web request
     * @param id   - User entity id
     * @return - ResponseEntity with the given body and status code
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<UserResponseDto> update(@Valid @RequestBody UserRequestDto user, @PathVariable Long id) {
        if (!Objects.equals(id, user.getId())) {
            throw new RuntimeException(localizedMessageSource.getMessage("controller.user.unexpectedId", new Object[]{}));
        }
        final UserResponseDto userResponseDto = mapper.map(userService.update(getUser(user)), UserResponseDto.class);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    /**
     * Deletes User entity by its id
     *
     * @param id - User entity id
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        userService.deleteById(id);
    }

    /**
     * Creates new User entity from the body of the web request.
     * Sets reference to a Role entity
     *
     * @param userRequestDto - the body of the web request
     * @return - new User entity
     */
    private User getUser(UserRequestDto userRequestDto) {
        final User user = mapper.map(userRequestDto, User.class);
        final Set<Role> roles = userRequestDto.getRolesId().stream().map(roleId -> {
            Role role = new Role();
            role.setId(roleId);
            return role;
        }).collect(Collectors.toSet());
        user.setRoles(roles);
        return user;
    }
}
