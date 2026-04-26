package com.bhavdip.travelease.dto.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {

    @NotBlank(message = "User name is required!")
    private String name;

    @NotBlank(message = "Email is required!")
    @Email(message = "Enter a valid email address")
    private String email;

    @NotBlank(message = "Password is required!")
    @Size(max = 16, min = 8, message = "Password must be between 8 to 16 characters!")
    private String password;
}
