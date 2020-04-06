package com.it.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * Response DTO (Data transfer object) class for changed password.
 */
@Getter
@Setter
public class NewPasswordResponseDto {

    private String userName;

    private String newPassword;

    private String passwordChangeSuccessMessage = "Password was changed successfully";

    public NewPasswordResponseDto(String userName, String newPassword) {
        this.userName = userName;
        this.newPassword = newPassword;
    }
}
