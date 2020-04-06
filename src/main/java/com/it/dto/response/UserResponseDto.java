package com.it.dto.response;

import com.it.dto.RoleDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * Response DTO (Data transfer object) class for User entity.
 */
@Getter
@Setter
public class UserResponseDto {

    private Long id;

    private String name;

    private String password;

    private Set<RoleDto> roles;
}
