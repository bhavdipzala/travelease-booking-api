package com.bhavdip.travelease.controller;


import com.bhavdip.travelease.dto.booking.BookingRequestDTO;
import com.bhavdip.travelease.dto.booking.BookingResponseDTO;
import com.bhavdip.travelease.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService){
        this.bookingService = bookingService;
    }


    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public BookingResponseDTO createBooking(@RequestBody @Valid BookingRequestDTO bookingRequest){
        return bookingService.createBooking(bookingRequest);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<BookingResponseDTO> getBookings( @RequestParam(required = false) Long userId,
                                       @RequestParam(required = false) String  bookingStatus) {

        if (userId != null && bookingStatus != null)
            return bookingService.getBookingsByUserAndStatus(userId, bookingStatus);
        if (userId != null)
            return bookingService.getBookingsByUser(userId);
        if (bookingStatus != null)
            return bookingService.getBookingsByStatus(bookingStatus);
        else
            return bookingService.getAllBookings();
    }

    @GetMapping("/{bookingId}")
    @PreAuthorize("isAuthenticated()")
    public BookingResponseDTO getBookingById(@PathVariable Long bookingId){
        return bookingService.getBookingById(bookingId);
    }

    @PatchMapping("/{bookingId}/cancel")
    @PreAuthorize("hasRole('ADMIN')")
    public BookingResponseDTO cancelBooking(@PathVariable Long bookingId){
        return bookingService.cancelBooking(bookingId);
    }

    @PatchMapping("/{bookingId}/update")
    @PreAuthorize("hasRole('ADMIN')")
    public BookingResponseDTO updateBooking(@PathVariable Long bookingId,
                                            @RequestParam Long packageId){
        return bookingService.updateBooking(bookingId, packageId);
    }


}
