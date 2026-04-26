package com.bhavdip.travelease.dto.travelpackage;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TravelPackageResponseDTO {

    private Long id;

    private String title;

    private String destination;

    private double price;

    private int days;

    private String category;

    private String description;

}
