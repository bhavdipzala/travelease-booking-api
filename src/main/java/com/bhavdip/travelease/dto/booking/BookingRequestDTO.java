package com.bhavdip.travelease.dto.booking;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingRequestDTO {

    @NotNull(message = "User ID is required!")
    private Long userId;

    @NotNull(message = "Package ID required!")
    private Long packageId;


}
