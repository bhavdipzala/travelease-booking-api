package com.bhavdip.travelease.service;

import com.bhavdip.travelease.dto.travelpackage.TravelPackageRequestDTO;
import com.bhavdip.travelease.dto.travelpackage.TravelPackageResponseDTO;
import com.bhavdip.travelease.exception.ResourceNotFoundException;
import com.bhavdip.travelease.model.TravelPackage;
import com.bhavdip.travelease.repository.TravelPackageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TravelPackageService {

    private final TravelPackageRepository travelPackageRepository;

    public TravelPackageService(TravelPackageRepository travelPackageRepository) {
        this.travelPackageRepository = travelPackageRepository;
    }


    private TravelPackageResponseDTO mapToTravelPackageResponseDTO(TravelPackage travelPackage){
        TravelPackageResponseDTO dto = new TravelPackageResponseDTO();

        dto.setId(travelPackage.getId());
        dto.setCategory(travelPackage.getCategory());
        dto.setPrice(travelPackage.getPrice());
        dto.setDays(travelPackage.getDays());
        dto.setDestination(travelPackage.getDestination());
        dto.setDescription(travelPackage.getDescription());
        dto.setTitle(travelPackage.getTitle());

        return dto;
    }


    public List<TravelPackageResponseDTO> getAllTravelPackages(){
        return travelPackageRepository.findAll()
                .stream()
                .map(this :: mapToTravelPackageResponseDTO)
                .toList();
    }

    public List<TravelPackageResponseDTO> getPackagesByDestination(String destination){
        return travelPackageRepository.findByDestination(destination)
                .stream()
                .map(this :: mapToTravelPackageResponseDTO)
                .toList();
    }

    public List<TravelPackageResponseDTO> getPackagesByTitle(String title){
        return travelPackageRepository.findByTitle(title)
                .stream()
                .map(this :: mapToTravelPackageResponseDTO)
                .toList();
    }

    public TravelPackageResponseDTO getTravelPackageById(Long packageId){
        TravelPackage travelPackage = travelPackageRepository.findById(packageId)
                .orElseThrow(() -> new ResourceNotFoundException("Package not found!"));

        return mapToTravelPackageResponseDTO(travelPackage);
    }

    public TravelPackageResponseDTO createTravelPackage (TravelPackageRequestDTO travelPackageRequest){

        TravelPackage travelPackage = new TravelPackage();
        travelPackage.setDays(travelPackageRequest.getDays());
        travelPackage.setCategory(travelPackageRequest.getCategory());
        travelPackage.setTitle(travelPackageRequest.getTitle());
        travelPackage.setPrice(travelPackageRequest.getPrice());
        travelPackage.setDestination(travelPackageRequest.getDestination());
        travelPackage.setDescription(travelPackageRequest.getDescription());

        TravelPackage savedTravelPackage = travelPackageRepository.save(travelPackage);

        return mapToTravelPackageResponseDTO(savedTravelPackage);
    }

    public TravelPackageResponseDTO updateTravelPackage(Long packageId, TravelPackageRequestDTO travelPackageRequest){
        TravelPackage existingTravelPackage = travelPackageRepository.findById(packageId)
                .orElseThrow(() -> new ResourceNotFoundException("Package not found!"));

        existingTravelPackage.setCategory(travelPackageRequest.getCategory());
        existingTravelPackage.setDays(travelPackageRequest.getDays());
        existingTravelPackage.setPrice(travelPackageRequest.getPrice());
        existingTravelPackage.setTitle(travelPackageRequest.getTitle());
        existingTravelPackage.setDescription(travelPackageRequest.getDescription());
        existingTravelPackage.setDestination(travelPackageRequest.getDestination());

        TravelPackage savedTravelPackage = travelPackageRepository.save(existingTravelPackage);

        return mapToTravelPackageResponseDTO(savedTravelPackage);
    }

    public void deleteTravelPackage(Long packageId){
        if (!travelPackageRepository.existsById(packageId)){
            throw new ResourceNotFoundException("Package not found!");
        }
        travelPackageRepository.deleteById(packageId);
    }
}

