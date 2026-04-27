package com.bhavdip.travelease.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserUpdateRequestDto {

    @NotBlank(message = "User name is required!")
    private String name;

    @NotBlank(message = "Email is required!")
    @Email(message = "Enter a valid email address")
    private String email;
}
