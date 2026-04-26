package com.bhavdip.travelease.repository;

import com.bhavdip.travelease.model.TravelPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelPackageRepository extends JpaRepository<TravelPackage, Long> {

    List<TravelPackage> findByTitle(String title);

    List<TravelPackage> findByDestination(String destination);


}
