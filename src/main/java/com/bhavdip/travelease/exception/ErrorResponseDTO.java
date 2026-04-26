package com.bhavdip.travelease.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class ErrorResponseDTO {

    private LocalDateTime timestamp;
    private String errorMessage;
    private int status;
}
