package com.parcelmanagement.service;

import com.parcelmanagement.dto.ApiResponse;
import com.parcelmanagement.entity.Booking;
import com.parcelmanagement.entity.Feedback;
import com.parcelmanagement.entity.User;
import com.parcelmanagement.repository.BookingRepository;
import com.parcelmanagement.repository.FeedbackRepository;
import com.parcelmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {
    
    @Autowired
    private FeedbackRepository feedbackRepository;
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public ApiResponse<String> addFeedback(String bookingId, String customerId, String description, Integer rating) {
        try {
            // Find booking
            Optional<Booking> bookingOptional = bookingRepository.findByBookingId(bookingId);
            if (bookingOptional.isEmpty()) {
                return ApiResponse.error("Booking not found");
            }
            
            Booking booking = bookingOptional.get();
            
            // Check if booking is delivered
            if (booking.getStatus() != Booking.BookingStatus.DELIVERED) {
                return ApiResponse.error("Feedback can only be added for delivered parcels");
            }
            
            // Find customer
            Optional<User> customerOptional = userRepository.findByCustomerId(customerId);
            if (customerOptional.isEmpty()) {
                return ApiResponse.error("Customer not found");
            }
            
            User customer = customerOptional.get();
            
            // Check if customer is the owner of the booking
            if (!booking.getCustomer().getId().equals(customer.getId())) {
                return ApiResponse.error("You can only provide feedback for your own bookings");
            }
            
            // Check if feedback already exists for this booking
            if (feedbackRepository.existsByBooking(booking)) {
                return ApiResponse.error("Feedback has already been provided for this booking");
            }
            
            // Create feedback
            Feedback feedback = new Feedback(booking, customer, description, rating);
            feedbackRepository.save(feedback);
            
            return ApiResponse.success("Feedback added successfully");
            
        } catch (Exception e) {
            return ApiResponse.error("Error adding feedback: " + e.getMessage());
        }
    }
    
    public ApiResponse<List<Booking>> getDeliveredBookingsForFeedback(String customerId) {
        try {
            Optional<User> customerOptional = userRepository.findByCustomerId(customerId);
            if (customerOptional.isEmpty()) {
                return ApiResponse.error("Customer not found");
            }
            
            List<Booking> deliveredBookings = bookingRepository.findDeliveredBookingsByCustomer(customerOptional.get());
            
            // Filter out bookings that already have feedback
            deliveredBookings.removeIf(booking -> feedbackRepository.existsByBooking(booking));
            
            return ApiResponse.success("Delivered bookings retrieved successfully", deliveredBookings);
            
        } catch (Exception e) {
            return ApiResponse.error("Error retrieving bookings: " + e.getMessage());
        }
    }
    
    public ApiResponse<Page<Feedback>> getAllFeedback(Pageable pageable) {
        try {
            Page<Feedback> feedback = feedbackRepository.findAll(pageable);
            
            return ApiResponse.success("Feedback retrieved successfully", feedback);
            
        } catch (Exception e) {
            return ApiResponse.error("Error retrieving feedback: " + e.getMessage());
        }
    }
    
    public ApiResponse<Page<Feedback>> getFeedbackByCustomer(String customerId, Pageable pageable) {
        try {
            Optional<User> customerOptional = userRepository.findByCustomerId(customerId);
            if (customerOptional.isEmpty()) {
                return ApiResponse.error("Customer not found");
            }
            
            Page<Feedback> feedback = feedbackRepository.findByCustomer(customerOptional.get(), pageable);
            
            return ApiResponse.success("Customer feedback retrieved successfully", feedback);
            
        } catch (Exception e) {
            return ApiResponse.error("Error retrieving customer feedback: " + e.getMessage());
        }
    }
    
    public ApiResponse<Page<Feedback>> searchFeedback(String searchTerm, Pageable pageable) {
        try {
            Page<Feedback> feedback = feedbackRepository.findBySearchTerm(searchTerm, pageable);
            
            return ApiResponse.success("Feedback retrieved successfully", feedback);
            
        } catch (Exception e) {
            return ApiResponse.error("Error searching feedback: " + e.getMessage());
        }
    }
    
    public ApiResponse<Page<Feedback>> getFeedbackByRating(Integer rating, Pageable pageable) {
        try {
            Page<Feedback> feedback = feedbackRepository.findByRating(rating, pageable);
            
            return ApiResponse.success("Feedback retrieved successfully", feedback);
            
        } catch (Exception e) {
            return ApiResponse.error("Error retrieving feedback by rating: " + e.getMessage());
        }
    }
    
    public ApiResponse<Double> getAverageRating() {
        try {
            Double averageRating = feedbackRepository.findAverageRating();
            
            return ApiResponse.success("Average rating calculated successfully", averageRating != null ? averageRating : 0.0);
            
        } catch (Exception e) {
            return ApiResponse.error("Error calculating average rating: " + e.getMessage());
        }
    }
}