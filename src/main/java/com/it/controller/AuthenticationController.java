package com.it.controller;

import com.it.component.LocalizedMessageSource;
import com.it.dto.request.NewPasswordRequestDto;
import com.it.dto.request.UserRegistrationRequestDTO;
import com.it.dto.request.UserSignInRequestDto;
import com.it.dto.response.NewPasswordResponseDto;
import com.it.dto.response.TokenResponseDTO;
import com.it.dto.response.UserResponseDto;
import com.it.model.Role;
import com.it.model.User;
import com.it.service.RoleService;
import com.it.service.UserService;
import com.it.service.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.dozer.Mapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Controller for authentication
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private final UserService userService;
    private final RoleService roleService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final Mapper mapper;
    private final LocalizedMessageSource localizedMessageSource;

    /**
     * Authenticate User and grants token, required to access the system
     *
     * @param userSignInRequestDto - the body of the web request
     * @return - valid token
     */
    @PostMapping("/signIn")
    public TokenResponseDTO authenticateUser(@Valid @RequestBody UserSignInRequestDto userSignInRequestDto) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userSignInRequestDto.getUsername(), userSignInRequestDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new TokenResponseDTO(tokenService.generate(authentication));
    }

    /**
     * Refreshes existing token
     *
     * @param token - current token
     * @return - new token
     */
    @PostMapping("/refresh")
    public TokenResponseDTO refreshToken(@RequestParam String token) {
        return new TokenResponseDTO(tokenService.refresh(token));
    }

    /**
     * Saves User entity with transferred parameters
     *
     * @param userRegistrationRequestDTO - the body of the web request
     * @return - ResponseEntity with the given body and status code
     */
    @PostMapping("/signUp")
    public UserResponseDto registerUser(@Valid @RequestBody UserRegistrationRequestDTO userRegistrationRequestDTO) {
        final User user = new User();
        user.setName(userRegistrationRequestDTO.getName());
        user.setPassword(userRegistrationRequestDTO.getPassword());
        final Set<Role> roles = userRegistrationRequestDTO.getRoles().stream()
                .map(roleService::findByName)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        user.setRoles(roles);
        return mapper.map(userService.save(user), UserResponseDto.class);
    }

    @PostMapping("/changePassword")
    public NewPasswordResponseDto changePassword(@Valid @RequestBody NewPasswordRequestDto newPasswordRequestDto) {
        final String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        final User user = userService.findByName(userName);
        if (!newPasswordRequestDto.getOldPassword().equals(newPasswordRequestDto.getConfirmOldPassword())) {
            throw new RuntimeException(localizedMessageSource.getMessage("controller.authentication.passwordDoesNotMatch", new Object[]{}));
        }
        userService.changePassword(user, newPasswordRequestDto.getNewPassword(), newPasswordRequestDto.getOldPassword());
        return new NewPasswordResponseDto(userName, newPasswordRequestDto.getNewPassword());
    }
}
