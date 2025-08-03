package com.parcelmanagement.service;

import com.parcelmanagement.dto.ApiResponse;
import com.parcelmanagement.dto.BookingDto;
import com.parcelmanagement.entity.Booking;
import com.parcelmanagement.entity.User;
import com.parcelmanagement.repository.BookingRepository;
import com.parcelmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookingService {
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public ApiResponse<Map<String, Object>> createBooking(BookingDto bookingDto, String customerId, String officerId) {
        try {
            // Find customer
            Optional<User> customerOptional = userRepository.findByCustomerId(customerId);
            if (customerOptional.isEmpty()) {
                return ApiResponse.error("Customer not found");
            }
            
            User customer = customerOptional.get();
            User officer = null;
            
            // If officer is booking on behalf of customer
            if (officerId != null) {
                Optional<User> officerOptional = userRepository.findByCustomerId(officerId);
                if (officerOptional.isEmpty()) {
                    return ApiResponse.error("Officer not found");
                }
                officer = officerOptional.get();
            }
            
            // Create booking
            Booking booking = new Booking();
            booking.setCustomer(customer);
            booking.setOfficer(officer);
            booking.setReceiverName(bookingDto.getReceiverName());
            booking.setReceiverAddress(bookingDto.getReceiverAddress());
            booking.setReceiverPin(bookingDto.getReceiverPin());
            booking.setReceiverMobile(bookingDto.getReceiverMobile());
            booking.setParcelWeight(bookingDto.getParcelWeight());
            booking.setContentsDescription(bookingDto.getContentsDescription());
            booking.setDeliveryType(bookingDto.getDeliveryType());
            booking.setPackingPreference(bookingDto.getPackingPreference());
            booking.setPickupTime(bookingDto.getPickupTime());
            booking.setDropoffTime(bookingDto.getDropoffTime());
            
            // Add admin fee if booked by officer
            if (officer != null) {
                booking.setAdminFee(BigDecimal.valueOf(50.0));
            }
            
            // Calculate total cost (done automatically in entity)
            booking.calculateTotalCost();
            
            Booking savedBooking = bookingRepository.save(booking);
            
            // Prepare response data
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("bookingId", savedBooking.getBookingId());
            responseData.put("customerId", customer.getCustomerId());
            responseData.put("customerName", customer.getName());
            responseData.put("customerAddress", customer.getAddress());
            responseData.put("customerMobile", customer.getCountryCode() + customer.getMobile());
            responseData.put("receiverName", savedBooking.getReceiverName());
            responseData.put("receiverAddress", savedBooking.getReceiverAddress());
            responseData.put("receiverMobile", savedBooking.getReceiverMobile());
            responseData.put("parcelWeight", savedBooking.getParcelWeight());
            responseData.put("contentsDescription", savedBooking.getContentsDescription());
            responseData.put("deliveryType", savedBooking.getDeliveryType());
            responseData.put("packingPreference", savedBooking.getPackingPreference());
            responseData.put("totalCost", savedBooking.getTotalCost());
            responseData.put("status", savedBooking.getStatus());
            responseData.put("bookingDate", savedBooking.getBookingDate());
            responseData.put("pickupTime", savedBooking.getPickupTime());
            responseData.put("dropoffTime", savedBooking.getDropoffTime());
            
            String message = officer != null ? 
                "Booking created successfully by officer" : 
                "Booking created successfully";
            
            return ApiResponse.success(message, responseData);
            
        } catch (Exception e) {
            return ApiResponse.error("Booking creation failed: " + e.getMessage());
        }
    }
    
    public ApiResponse<Booking> getBookingById(String bookingId) {
        try {
            Optional<Booking> bookingOptional = bookingRepository.findByBookingId(bookingId);
            
            if (bookingOptional.isEmpty()) {
                return ApiResponse.error("Booking not found");
            }
            
            return ApiResponse.success("Booking found", bookingOptional.get());
            
        } catch (Exception e) {
            return ApiResponse.error("Error retrieving booking: " + e.getMessage());
        }
    }
    
    public ApiResponse<List<Booking>> getBookingsByCustomer(String customerId) {
        try {
            Optional<User> customerOptional = userRepository.findByCustomerId(customerId);
            if (customerOptional.isEmpty()) {
                return ApiResponse.error("Customer not found");
            }
            
            List<Booking> bookings = bookingRepository.findByCustomer(customerOptional.get());
            
            return ApiResponse.success("Bookings retrieved successfully", bookings);
            
        } catch (Exception e) {
            return ApiResponse.error("Error retrieving bookings: " + e.getMessage());
        }
    }
    
    public ApiResponse<Page<Booking>> getBookingsByCustomerPaginated(String customerId, Pageable pageable) {
        try {
            Optional<User> customerOptional = userRepository.findByCustomerId(customerId);
            if (customerOptional.isEmpty()) {
                return ApiResponse.error("Customer not found");
            }
            
            Page<Booking> bookings = bookingRepository.findByCustomerAndStatusNotCancelled(
                customerOptional.get(), pageable);
            
            return ApiResponse.success("Bookings retrieved successfully", bookings);
            
        } catch (Exception e) {
            return ApiResponse.error("Error retrieving bookings: " + e.getMessage());
        }
    }
    
    public ApiResponse<Page<Booking>> getAllBookings(Pageable pageable) {
        try {
            Page<Booking> bookings = bookingRepository.findAll(pageable);
            
            return ApiResponse.success("All bookings retrieved successfully", bookings);
            
        } catch (Exception e) {
            return ApiResponse.error("Error retrieving bookings: " + e.getMessage());
        }
    }
    
    public ApiResponse<Page<Booking>> searchBookings(String searchTerm, Pageable pageable) {
        try {
            Page<Booking> bookings = bookingRepository.findBySearchTerm(searchTerm, pageable);
            
            return ApiResponse.success("Bookings retrieved successfully", bookings);
            
        } catch (Exception e) {
            return ApiResponse.error("Error searching bookings: " + e.getMessage());
        }
    }
    
    public ApiResponse<String> updateBookingStatus(String bookingId, Booking.BookingStatus status) {
        try {
            Optional<Booking> bookingOptional = bookingRepository.findByBookingId(bookingId);
            
            if (bookingOptional.isEmpty()) {
                return ApiResponse.error("Booking not found");
            }
            
            Booking booking = bookingOptional.get();
            
            // Validate status transitions
            if ((booking.getStatus() == Booking.BookingStatus.DELIVERED || 
                 booking.getStatus() == Booking.BookingStatus.IN_TRANSIT) && 
                status == Booking.BookingStatus.CANCELLED) {
                return ApiResponse.error("Cannot cancel delivered or in-transit bookings");
            }
            
            booking.setStatus(status);
            bookingRepository.save(booking);
            
            return ApiResponse.success("Booking status updated successfully to " + status);
            
        } catch (Exception e) {
            return ApiResponse.error("Error updating booking status: " + e.getMessage());
        }
    }
    
    public ApiResponse<String> updatePickupDropoffTime(String bookingId, LocalDateTime pickupTime, LocalDateTime dropoffTime) {
        try {
            Optional<Booking> bookingOptional = bookingRepository.findByBookingId(bookingId);
            
            if (bookingOptional.isEmpty()) {
                return ApiResponse.error("Booking not found");
            }
            
            Booking booking = bookingOptional.get();
            booking.setPickupTime(pickupTime);
            booking.setDropoffTime(dropoffTime);
            bookingRepository.save(booking);
            
            return ApiResponse.success("Pickup and drop-off times updated successfully");
            
        } catch (Exception e) {
            return ApiResponse.error("Error updating pickup/drop-off times: " + e.getMessage());
        }
    }
    
    public ApiResponse<String> cancelBooking(String bookingId, String userRole) {
        try {
            Optional<Booking> bookingOptional = bookingRepository.findByBookingId(bookingId);
            
            if (bookingOptional.isEmpty()) {
                return ApiResponse.error("Booking cancel failed, incorrect Booking ID");
            }
            
            Booking booking = bookingOptional.get();
            
            // Check if booking can be cancelled
            if (booking.getStatus() != Booking.BookingStatus.BOOKED) {
                return ApiResponse.error("Booking cancel failed");
            }
            
            // Check if it's delivered or in transit (cannot cancel)
            if (booking.getStatus() == Booking.BookingStatus.DELIVERED || 
                booking.getStatus() == Booking.BookingStatus.IN_TRANSIT) {
                return ApiResponse.error("Cannot cancel delivered or in-transit bookings");
            }
            
            booking.setStatus(Booking.BookingStatus.CANCELLED);
            bookingRepository.save(booking);
            
            String message;
            if ("OFFICER".equals(userRole)) {
                message = "Booking cancelled successfully. Booking amount will be refunded to the customer account within 5 working days. " +
                         "Booking ID: " + booking.getBookingId() + ", Booking Amount: ₹" + booking.getTotalCost();
            } else {
                message = "Booking cancelled successfully";
            }
            
            return ApiResponse.success(message);
            
        } catch (Exception e) {
            return ApiResponse.error("Booking cancel failed");
        }
    }
    
    public ApiResponse<BigDecimal> calculateBookingCost(BookingDto bookingDto) {
        try {
            // Create temporary booking to calculate cost
            Booking tempBooking = new Booking();
            tempBooking.setParcelWeight(bookingDto.getParcelWeight());
            tempBooking.setDeliveryType(bookingDto.getDeliveryType());
            tempBooking.setPackingPreference(bookingDto.getPackingPreference());
            
            // Add admin fee if requested (for officer bookings)
            if (bookingDto.getCustomerId() != null) {
                tempBooking.setAdminFee(BigDecimal.valueOf(50.0));
            }
            
            tempBooking.calculateTotalCost();
            
            return ApiResponse.success("Cost calculated successfully", tempBooking.getTotalCost());
            
        } catch (Exception e) {
            return ApiResponse.error("Error calculating cost: " + e.getMessage());
        }
    }
}