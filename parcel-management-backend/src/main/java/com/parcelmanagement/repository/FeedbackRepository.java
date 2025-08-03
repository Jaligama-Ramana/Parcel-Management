package com.parcelmanagement.repository;

import com.parcelmanagement.entity.Booking;
import com.parcelmanagement.entity.Feedback;
import com.parcelmanagement.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    
    Optional<Feedback> findByBooking(Booking booking);
    
    List<Feedback> findByCustomer(User customer);
    
    Page<Feedback> findByCustomer(User customer, Pageable pageable);
    
    @Query("SELECT f FROM Feedback f WHERE f.booking.bookingId = :bookingId")
    Optional<Feedback> findByBookingId(@Param("bookingId") String bookingId);
    
    @Query("SELECT f FROM Feedback f WHERE f.rating = :rating")
    Page<Feedback> findByRating(@Param("rating") Integer rating, Pageable pageable);
    
    @Query("SELECT f FROM Feedback f WHERE f.rating >= :minRating")
    Page<Feedback> findByRatingGreaterThanEqual(@Param("minRating") Integer minRating, Pageable pageable);
    
    @Query("SELECT f FROM Feedback f WHERE f.description LIKE %:search% " +
           "OR f.customer.name LIKE %:search% " +
           "OR f.booking.bookingId LIKE %:search%")
    Page<Feedback> findBySearchTerm(@Param("search") String search, Pageable pageable);
    
    @Query("SELECT AVG(f.rating) FROM Feedback f")
    Double findAverageRating();
    
    @Query("SELECT COUNT(f) FROM Feedback f WHERE f.rating = :rating")
    long countByRating(@Param("rating") Integer rating);
    
    boolean existsByBooking(Booking booking);
}