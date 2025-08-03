package com.parcelmanagement.repository;

import com.parcelmanagement.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByCustomerId(String customerId);
    
    Optional<User> findByEmail(String email);
    
    Optional<User> findByCustomerIdAndPassword(String customerId, String password);
    
    boolean existsByEmail(String email);
    
    boolean existsByCustomerId(String customerId);
    
    @Query("SELECT u FROM User u WHERE u.role = :role")
    Page<User> findByRole(@Param("role") User.Role role, Pageable pageable);
    
    @Query("SELECT u FROM User u WHERE u.customerId LIKE %:search% " +
           "OR u.name LIKE %:search% OR u.email LIKE %:search%")
    Page<User> findBySearchTerm(@Param("search") String search, Pageable pageable);
}