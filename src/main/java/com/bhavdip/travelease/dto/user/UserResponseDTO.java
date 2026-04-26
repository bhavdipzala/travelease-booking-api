package com.bhavdip.travelease.dto.user;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {

    private Long id;

    private String name;

    private String email;

    private String role;

    private boolean blocked;

}