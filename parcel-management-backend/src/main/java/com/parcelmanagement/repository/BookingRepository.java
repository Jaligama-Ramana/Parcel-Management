package com.parcelmanagement.repository;

import com.parcelmanagement.entity.Booking;
import com.parcelmanagement.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    
    Optional<Booking> findByBookingId(String bookingId);
    
    List<Booking> findByCustomer(User customer);
    
    Page<Booking> findByCustomer(User customer, Pageable pageable);
    
    @Query("SELECT b FROM Booking b WHERE b.customer = :customer AND b.status != 'CANCELLED'")
    Page<Booking> findByCustomerAndStatusNotCancelled(@Param("customer") User customer, Pageable pageable);
    
    @Query("SELECT b FROM Booking b WHERE b.status = :status")
    Page<Booking> findByStatus(@Param("status") Booking.BookingStatus status, Pageable pageable);
    
    @Query("SELECT b FROM Booking b WHERE b.customer.customerId = :customerId")
    Page<Booking> findByCustomerCustomerId(@Param("customerId") String customerId, Pageable pageable);
    
    @Query("SELECT b FROM Booking b WHERE b.bookingId LIKE %:search% " +
           "OR b.customer.customerId LIKE %:search% " +
           "OR b.customer.name LIKE %:search% " +
           "OR b.receiverName LIKE %:search%")
    Page<Booking> findBySearchTerm(@Param("search") String search, Pageable pageable);
    
    @Query("SELECT b FROM Booking b WHERE b.customer = :customer AND " +
           "(b.bookingId LIKE %:search% OR b.receiverName LIKE %:search% OR b.status = :status)")
    Page<Booking> findByCustomerAndSearchTerm(@Param("customer") User customer, 
                                              @Param("search") String search,
                                              @Param("status") Booking.BookingStatus status,
                                              Pageable pageable);
    
    @Query("SELECT b FROM Booking b WHERE b.bookingDate BETWEEN :startDate AND :endDate")
    Page<Booking> findByBookingDateBetween(@Param("startDate") LocalDateTime startDate,
                                           @Param("endDate") LocalDateTime endDate,
                                           Pageable pageable);
    
    @Query("SELECT b FROM Booking b WHERE b.customer = :customer AND b.status = 'DELIVERED'")
    List<Booking> findDeliveredBookingsByCustomer(@Param("customer") User customer);
    
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.status = :status")
    long countByStatus(@Param("status") Booking.BookingStatus status);
}