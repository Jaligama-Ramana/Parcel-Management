package com.parcelmanagement.service;

import com.parcelmanagement.dto.ApiResponse;
import com.parcelmanagement.dto.PaymentDto;
import com.parcelmanagement.entity.Booking;
import com.parcelmanagement.entity.Payment;
import com.parcelmanagement.repository.BookingRepository;
import com.parcelmanagement.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class PaymentService {
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private BookingRepository bookingRepository;
    
    public ApiResponse<Map<String, Object>> processPayment(PaymentDto paymentDto) {
        try {
            // Find booking
            Optional<Booking> bookingOptional = bookingRepository.findByBookingId(paymentDto.getBookingId());
            if (bookingOptional.isEmpty()) {
                return ApiResponse.error("Booking not found");
            }
            
            Booking booking = bookingOptional.get();
            
            // Check if payment already exists for this booking
            Optional<Payment> existingPayment = paymentRepository.findByBooking(booking);
            if (existingPayment.isPresent()) {
                return ApiResponse.error("Payment already processed for this booking");
            }
            
            // Validate card expiry date
            if (!isValidExpiryDate(paymentDto.getExpiryMonth(), paymentDto.getExpiryYear())) {
                return ApiResponse.error("Card has expired or invalid expiry date");
            }
            
            // Simulate payment processing (in real scenario, integrate with payment gateway)
            boolean paymentSuccess = simulatePaymentProcessing(paymentDto);
            
            if (!paymentSuccess) {
                return ApiResponse.error("Payment processing failed. Please check your card details and try again.");
            }
            
            // Create payment record
            Payment payment = new Payment();
            payment.setBooking(booking);
            payment.setAmount(paymentDto.getAmount());
            payment.setPaymentMethod(paymentDto.getPaymentMethod());
            payment.setCardType(paymentDto.getCardType());
            payment.setCardholderName(paymentDto.getCardholderName());
            payment.setCardNumberMasked(maskCardNumber(paymentDto.getCardNumber()));
            payment.setStatus(Payment.PaymentStatus.COMPLETED);
            
            Payment savedPayment = paymentRepository.save(payment);
            
            // Update booking status to BOOKED after successful payment
            booking.setStatus(Booking.BookingStatus.BOOKED);
            bookingRepository.save(booking);
            
            // Prepare response data
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("paymentId", savedPayment.getPaymentId());
            responseData.put("transactionId", savedPayment.getTransactionId());
            responseData.put("bookingId", booking.getBookingId());
            responseData.put("amount", savedPayment.getAmount());
            responseData.put("paymentDate", savedPayment.getPaymentDate());
            responseData.put("cardType", savedPayment.getCardType());
            responseData.put("status", savedPayment.getStatus());
            responseData.put("cardNumberMasked", savedPayment.getCardNumberMasked());
            responseData.put("cardholderName", savedPayment.getCardholderName());
            
            return ApiResponse.success("Payment processed successfully", responseData);
            
        } catch (Exception e) {
            return ApiResponse.error("Payment processing failed: " + e.getMessage());
        }
    }
    
    public ApiResponse<Map<String, Object>> processOfficeCounterPayment(String bookingId) {
        try {
            // Find booking
            Optional<Booking> bookingOptional = bookingRepository.findByBookingId(bookingId);
            if (bookingOptional.isEmpty()) {
                return ApiResponse.error("Booking not found");
            }
            
            Booking booking = bookingOptional.get();
            
            // Check if payment already exists for this booking
            Optional<Payment> existingPayment = paymentRepository.findByBooking(booking);
            if (existingPayment.isPresent()) {
                return ApiResponse.error("Payment already processed for this booking");
            }
            
            // Create payment record for office counter payment
            Payment payment = new Payment();
            payment.setBooking(booking);
            payment.setAmount(booking.getTotalCost());
            payment.setPaymentMethod(Payment.PaymentMethod.OFFICE_COUNTER);
            payment.setCardType(Payment.CardType.CREDIT); // Default for office payments
            payment.setCardholderName("Office Counter Payment");
            payment.setCardNumberMasked("****-****-****-0000");
            payment.setStatus(Payment.PaymentStatus.COMPLETED);
            
            Payment savedPayment = paymentRepository.save(payment);
            
            // Update booking status to BOOKED after payment
            booking.setStatus(Booking.BookingStatus.BOOKED);
            bookingRepository.save(booking);
            
            // Prepare response data
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("paymentId", savedPayment.getPaymentId());
            responseData.put("transactionId", savedPayment.getTransactionId());
            responseData.put("bookingId", booking.getBookingId());
            responseData.put("amount", savedPayment.getAmount());
            responseData.put("paymentDate", savedPayment.getPaymentDate());
            responseData.put("paymentMethod", "Office Counter");
            responseData.put("status", savedPayment.getStatus());
            
            return ApiResponse.success("Office counter payment processed successfully", responseData);
            
        } catch (Exception e) {
            return ApiResponse.error("Payment processing failed: " + e.getMessage());
        }
    }
    
    public ApiResponse<Payment> getPaymentByBookingId(String bookingId) {
        try {
            Optional<Payment> paymentOptional = paymentRepository.findByBookingId(bookingId);
            
            if (paymentOptional.isEmpty()) {
                return ApiResponse.error("Payment not found for this booking");
            }
            
            return ApiResponse.success("Payment found", paymentOptional.get());
            
        } catch (Exception e) {
            return ApiResponse.error("Error retrieving payment: " + e.getMessage());
        }
    }
    
    public ApiResponse<Payment> getPaymentByPaymentId(String paymentId) {
        try {
            Optional<Payment> paymentOptional = paymentRepository.findByPaymentId(paymentId);
            
            if (paymentOptional.isEmpty()) {
                return ApiResponse.error("Payment not found");
            }
            
            return ApiResponse.success("Payment found", paymentOptional.get());
            
        } catch (Exception e) {
            return ApiResponse.error("Error retrieving payment: " + e.getMessage());
        }
    }
    
    public ApiResponse<Page<Payment>> getAllPayments(Pageable pageable) {
        try {
            Page<Payment> payments = paymentRepository.findAll(pageable);
            
            return ApiResponse.success("Payments retrieved successfully", payments);
            
        } catch (Exception e) {
            return ApiResponse.error("Error retrieving payments: " + e.getMessage());
        }
    }
    
    public ApiResponse<Page<Payment>> searchPayments(String searchTerm, Pageable pageable) {
        try {
            Page<Payment> payments = paymentRepository.findBySearchTerm(searchTerm, pageable);
            
            return ApiResponse.success("Payments retrieved successfully", payments);
            
        } catch (Exception e) {
            return ApiResponse.error("Error searching payments: " + e.getMessage());
        }
    }
    
    private boolean isValidExpiryDate(String month, String year) {
        try {
            int expiryMonth = Integer.parseInt(month);
            int expiryYear = Integer.parseInt("20" + year); // Assuming 2-digit year
            
            LocalDate currentDate = LocalDate.now();
            LocalDate expiryDate = LocalDate.of(expiryYear, expiryMonth, 1).plusMonths(1).minusDays(1);
            
            return !expiryDate.isBefore(currentDate);
            
        } catch (Exception e) {
            return false;
        }
    }
    
    private boolean simulatePaymentProcessing(PaymentDto paymentDto) {
        // Simulate payment processing logic
        // In real scenario, this would integrate with payment gateway like Stripe, PayPal, etc.
        
        // Simple validation: reject if card number starts with '0000'
        if (paymentDto.getCardNumber().startsWith("0000")) {
            return false;
        }
        
        // Simulate 95% success rate
        return Math.random() > 0.05;
    }
    
    private String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            return "****-****-****-0000";
        }
        
        String lastFour = cardNumber.substring(cardNumber.length() - 4);
        return "****-****-****-" + lastFour;
    }
}