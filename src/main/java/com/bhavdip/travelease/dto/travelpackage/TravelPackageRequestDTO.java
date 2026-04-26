package com.bhavdip.travelease.dto.travelpackage;


import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TravelPackageRequestDTO {

    @NotBlank(message = "Title is required!")
    private String title;

    @NotBlank(message = "Destination is required!")
    private String destination;

    @NotNull(message = "Price is required!")
    @PositiveOrZero(message = "Price must not be negative!")
    private Double price;

    @NotNull(message = "Days required!")
    @Positive(message = "Days must be greater than zero!")
    private Integer days;


    private String category;

    @NotBlank(message = "Description is required!")
    @Size(max = 1000, message = "Description too long!")
    private String description;

}
