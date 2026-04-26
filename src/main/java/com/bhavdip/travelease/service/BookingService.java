package com.bhavdip.travelease.service;


import com.bhavdip.travelease.dto.booking.BookingRequestDTO;
import com.bhavdip.travelease.dto.booking.BookingResponseDTO;
import com.bhavdip.travelease.exception.BadRequestException;
import com.bhavdip.travelease.exception.ResourceNotFoundException;
import com.bhavdip.travelease.model.Booking;
import com.bhavdip.travelease.model.BookingStatus;
import com.bhavdip.travelease.model.TravelPackage;
import com.bhavdip.travelease.model.User;
import com.bhavdip.travelease.repository.BookingRepository;
import com.bhavdip.travelease.repository.TravelPackageRepository;
import com.bhavdip.travelease.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final TravelPackageRepository travelPackageRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository, TravelPackageRepository travelPackageRepository,
                          UserRepository userRepository){
        this.bookingRepository = bookingRepository;
        this.travelPackageRepository = travelPackageRepository;
        this.userRepository = userRepository;
    }


    private BookingResponseDTO mapToBookingResponseDTO(Booking booking){
        BookingResponseDTO dto = new BookingResponseDTO();

        dto.setBookingId(booking.getId());
        dto.setBookingDate(booking.getBookingDate());
        dto.setStatus(booking.getStatus().name());
        dto.setUserId(booking.getUser().getId());
        dto.setUserName(booking.getUser().getName());
        dto.setPackageId(booking.getTravelPackage().getId());
        dto.setPackageName(booking.getTravelPackage().getTitle());
        dto.setPackageLink("/api/packages/" + booking.getTravelPackage().getId());

        return dto;

    }


    @Transactional
    public BookingResponseDTO createBooking(BookingRequestDTO bookingRequest){
        User user = userRepository.findById(bookingRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        if (user.getBlocked()){
            throw new BadRequestException("User is blocked!");
        }

        TravelPackage travelPackage = travelPackageRepository.findById(bookingRequest.getPackageId())
                .orElseThrow(() -> new ResourceNotFoundException("Travel Package not found!"));

        Booking booking = new Booking();

        booking.setUser(user);
        booking.setTravelPackage(travelPackage);
        booking.setStatus(BookingStatus.BOOKED);
        booking.setBookingDate(LocalDateTime.now());

        Booking savedBooking = bookingRepository.save(booking);

        return mapToBookingResponseDTO(savedBooking);
    }


    public List<BookingResponseDTO> getAllBookings(){
        return bookingRepository.findAll()
                .stream()
                .map(this :: mapToBookingResponseDTO)
                .toList();
    }

    public BookingResponseDTO getBookingById(Long bookingId){
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found!"));

        return mapToBookingResponseDTO(booking);
    }

    public List<BookingResponseDTO> getBookingsByUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        return bookingRepository.findByUser(user)
                .stream()
                .map(this :: mapToBookingResponseDTO)
                .toList();
    }


    public BookingResponseDTO cancelBooking(Long bookingId){
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found!"));

        if (booking.getStatus() == BookingStatus.CANCELLED){
            throw new BadRequestException("Booking already cancelled!");
        }
        if (booking.getStatus() == BookingStatus.FAILED){
            throw new BadRequestException("Booking was failed!");
        }

        booking.setStatus(BookingStatus.CANCELLED);

        Booking savedBooking = bookingRepository.save(booking);

        return mapToBookingResponseDTO(savedBooking);
    }

    public BookingResponseDTO updateBooking(Long bookingId, Long newPackageId){
        Booking currentBooking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found!"));

        if (currentBooking.getStatus() == BookingStatus.CANCELLED){
            throw new BadRequestException("Booking already cancelled!");
        }
        if (currentBooking.getStatus() == BookingStatus.FAILED){
            throw new BadRequestException("Booking was failed!");
        }

        TravelPackage newTravelPackage = travelPackageRepository.findById(newPackageId)
                        .orElseThrow(() -> new ResourceNotFoundException("Travel package not found!"));

        currentBooking.setTravelPackage(newTravelPackage);

        Booking updatedBooking = bookingRepository.save(currentBooking);

        return mapToBookingResponseDTO(updatedBooking);

    }


    private BookingStatus verifyBookingStatus(String bookingStatusPassed){
        try {
            return BookingStatus.valueOf(bookingStatusPassed.toUpperCase());
        } catch (RuntimeException e){
            throw new BadRequestException("Invalid booking status: "+bookingStatusPassed);
        }
    }


    public List<BookingResponseDTO> getBookingsByStatus(String bookingStatus){
        return bookingRepository.findByStatus(verifyBookingStatus(bookingStatus))
                .stream()
                .map(this :: mapToBookingResponseDTO)
                .toList();
    }

    public List<BookingResponseDTO> getBookingsByUserAndStatus(Long userId, String bookingStatus){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        return bookingRepository.findByUserAndStatus(user, verifyBookingStatus(bookingStatus))
                .stream()
                .map(this :: mapToBookingResponseDTO)
                .toList();
    }


}
