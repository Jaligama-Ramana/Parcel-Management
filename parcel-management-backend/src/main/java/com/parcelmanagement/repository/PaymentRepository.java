package com.parcelmanagement.repository;

import com.parcelmanagement.entity.Booking;
import com.parcelmanagement.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
    Optional<Payment> findByPaymentId(String paymentId);
    
    Optional<Payment> findByTransactionId(String transactionId);
    
    Optional<Payment> findByBooking(Booking booking);
    
    @Query("SELECT p FROM Payment p WHERE p.booking.bookingId = :bookingId")
    Optional<Payment> findByBookingId(@Param("bookingId") String bookingId);
    
    @Query("SELECT p FROM Payment p WHERE p.status = :status")
    Page<Payment> findByStatus(@Param("status") Payment.PaymentStatus status, Pageable pageable);
    
    @Query("SELECT p FROM Payment p WHERE p.paymentMethod = :method")
    Page<Payment> findByPaymentMethod(@Param("method") Payment.PaymentMethod method, Pageable pageable);
    
    @Query("SELECT p FROM Payment p WHERE p.paymentId LIKE %:search% " +
           "OR p.transactionId LIKE %:search% " +
           "OR p.booking.bookingId LIKE %:search%")
    Page<Payment> findBySearchTerm(@Param("search") String search, Pageable pageable);
}