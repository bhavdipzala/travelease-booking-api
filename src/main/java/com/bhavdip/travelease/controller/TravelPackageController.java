package com.bhavdip.travelease.controller;


import com.bhavdip.travelease.dto.travelpackage.TravelPackageRequestDTO;
import com.bhavdip.travelease.dto.travelpackage.TravelPackageResponseDTO;
import com.bhavdip.travelease.service.TravelPackageService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/packages")
public class TravelPackageController {

    private final TravelPackageService travelPackageService;

    public TravelPackageController (TravelPackageService travelPackageService){
        this.travelPackageService = travelPackageService;
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public TravelPackageResponseDTO createTravelPackage(@RequestBody @Valid TravelPackageRequestDTO travelPackageRequest){
        return travelPackageService.createTravelPackage(travelPackageRequest);
    }

    @GetMapping
    public List<TravelPackageResponseDTO> getAllTravelPackages(@RequestParam(required = false) String destination,
                                                               @RequestParam(required = false) String title){
        if (destination != null)
            return travelPackageService.getPackagesByDestination(destination);
        if (title != null)
            return travelPackageService.getPackagesByTitle(title);

        return travelPackageService.getAllTravelPackages();
    }

    @GetMapping("/{packageId}")
    public TravelPackageResponseDTO getTravelPackageById(@PathVariable Long packageId){
        return travelPackageService.getTravelPackageById(packageId);
    }

    @PatchMapping("/{packageId}")
    @PreAuthorize("hasRole('ADMIN')")
    public TravelPackageResponseDTO updateTravelPackage(@PathVariable Long packageId,
                                                        @RequestBody @Valid TravelPackageRequestDTO travelPackageRequest){
        return travelPackageService.updateTravelPackage(packageId, travelPackageRequest);
    }

    @DeleteMapping("/{packageId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteTravelPackage(@PathVariable Long packageId){
        travelPackageService.deleteTravelPackage(packageId);
    }


}
