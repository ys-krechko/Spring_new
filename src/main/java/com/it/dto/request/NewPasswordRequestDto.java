package com.it.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Request DTO (Data transfer object) class for changing password
 */
@Getter
@Setter
public class NewPasswordRequestDto {

    @NotNull(message = "user.oldPassword.notNull")
    @NotEmpty(message = "user.oldPassword.notEmpty")
    private String oldPassword;

    @NotNull(message = "user.confirmOldPassword.notNull")
    @NotEmpty(message = "user.confirmOldPassword.notEmpty")
    private String confirmOldPassword;

    @NotNull(message = "user.newPassword.notNull")
    @NotEmpty(message = "user.newPassword.notEmpty")
    @Size(message = "user.newPassword.size")
    private String newPassword;
}
