package com.bhavdip.travelease.repository;

import com.bhavdip.travelease.model.Booking;
import com.bhavdip.travelease.model.BookingStatus;
import com.bhavdip.travelease.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUser(User user);

    List<Booking> findByStatus(BookingStatus status);

    List<Booking> findByUserAndStatus(User user, BookingStatus bookingStatus);
}
