package com.bhavdip.travelease.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "travel_packages")
@Getter
@Setter
public class TravelPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String destination;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer days;

    private String category;

    @Column(length = 1000, nullable = false)
    private String description;
}
