package com.bhavdip.travelease.dto.booking;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookingResponseDTO {

    private Long bookingId;

    private Long userId;
    private String userName;

    private Long packageId;
    private String packageName;
    private String packageLink;

    private String status;

    private LocalDateTime bookingDate;

}
