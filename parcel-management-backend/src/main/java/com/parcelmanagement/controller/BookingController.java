package com.parcelmanagement.controller;

import com.parcelmanagement.dto.ApiResponse;
import com.parcelmanagement.dto.BookingDto;
import com.parcelmanagement.entity.Booking;
import com.parcelmanagement.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bookings")
@CrossOrigin(origins = "http://localhost:4200")
public class BookingController {
    
    @Autowired
    private BookingService bookingService;
    
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Map<String, Object>>> createBooking(
            @Valid @RequestBody BookingDto bookingDto,
            @RequestParam String customerId,
            @RequestParam(required = false) String officerId) {
        
        ApiResponse<Map<String, Object>> response = bookingService.createBooking(bookingDto, customerId, officerId);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PostMapping("/calculate-cost")
    public ResponseEntity<ApiResponse<BigDecimal>> calculateCost(@Valid @RequestBody BookingDto bookingDto) {
        ApiResponse<BigDecimal> response = bookingService.calculateBookingCost(bookingDto);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @GetMapping("/{bookingId}")
    public ResponseEntity<ApiResponse<Booking>> getBookingById(@PathVariable String bookingId) {
        ApiResponse<Booking> response = bookingService.getBookingById(bookingId);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<List<Booking>>> getBookingsByCustomer(@PathVariable String customerId) {
        ApiResponse<List<Booking>> response = bookingService.getBookingsByCustomer(customerId);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @GetMapping("/customer/{customerId}/paginated")
    public ResponseEntity<ApiResponse<Page<Booking>>> getBookingsByCustomerPaginated(
            @PathVariable String customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "bookingDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        ApiResponse<Page<Booking>> response = bookingService.getBookingsByCustomerPaginated(customerId, pageable);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<Page<Booking>>> getAllBookings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "bookingDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        ApiResponse<Page<Booking>> response = bookingService.getAllBookings(pageable);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<Booking>>> searchBookings(
            @RequestParam String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "bookingDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        ApiResponse<Page<Booking>> response = bookingService.searchBookings(searchTerm, pageable);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PutMapping("/{bookingId}/status")
    public ResponseEntity<ApiResponse<String>> updateBookingStatus(
            @PathVariable String bookingId,
            @RequestParam Booking.BookingStatus status) {
        
        ApiResponse<String> response = bookingService.updateBookingStatus(bookingId, status);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PutMapping("/{bookingId}/times")
    public ResponseEntity<ApiResponse<String>> updatePickupDropoffTime(
            @PathVariable String bookingId,
            @RequestParam(required = false) LocalDateTime pickupTime,
            @RequestParam(required = false) LocalDateTime dropoffTime) {
        
        ApiResponse<String> response = bookingService.updatePickupDropoffTime(bookingId, pickupTime, dropoffTime);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<ApiResponse<String>> cancelBooking(
            @PathVariable String bookingId,
            @RequestParam String userRole) {
        
        ApiResponse<String> response = bookingService.cancelBooking(bookingId, userRole);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}