package com.parcelmanagement.service;

import com.parcelmanagement.dto.ApiResponse;
import com.parcelmanagement.dto.LoginDto;
import com.parcelmanagement.dto.UserRegistrationDto;
import com.parcelmanagement.entity.User;
import com.parcelmanagement.repository.UserRepository;
import com.parcelmanagement.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    public ApiResponse<Map<String, Object>> registerCustomer(UserRegistrationDto registrationDto) {
        try {
            // Validate password confirmation
            if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
                return ApiResponse.error("Password and confirm password do not match");
            }
            
            // Check if email already exists
            if (userRepository.existsByEmail(registrationDto.getEmail())) {
                return ApiResponse.error("Email already exists");
            }
            
            // Create new user
            User user = new User();
            user.setName(registrationDto.getName());
            user.setEmail(registrationDto.getEmail());
            user.setCountryCode(registrationDto.getCountryCode());
            user.setMobile(registrationDto.getMobile());
            user.setAddress(registrationDto.getAddress());
            user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
            user.setRole(User.Role.CUSTOMER);
            user.setNotificationPreference(registrationDto.isNotificationPreference());
            user.setMailDeliveryPreference(registrationDto.isMailDeliveryPreference());
            
            User savedUser = userRepository.save(user);
            
            // Prepare response data
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("customerId", savedUser.getCustomerId());
            responseData.put("name", savedUser.getName());
            responseData.put("email", savedUser.getEmail());
            
            return ApiResponse.success("Customer Registration successful.", responseData);
            
        } catch (Exception e) {
            return ApiResponse.error("Registration failed: " + e.getMessage());
        }
    }
    
    public ApiResponse<Map<String, Object>> login(LoginDto loginDto) {
        try {
            Optional<User> userOptional = userRepository.findByCustomerId(loginDto.getCustomerId());
            
            if (userOptional.isEmpty()) {
                return ApiResponse.error("Invalid customer ID or password");
            }
            
            User user = userOptional.get();
            
            if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
                return ApiResponse.error("Invalid customer ID or password");
            }
            
            // Generate JWT token
            String token = jwtUtil.generateToken(user.getCustomerId(), user.getRole().name());
            
            // Prepare response data
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("token", token);
            responseData.put("customerId", user.getCustomerId());
            responseData.put("name", user.getName());
            responseData.put("email", user.getEmail());
            responseData.put("role", user.getRole().name());
            responseData.put("address", user.getAddress());
            responseData.put("mobile", user.getCountryCode() + user.getMobile());
            
            return ApiResponse.success("Login successful", responseData);
            
        } catch (Exception e) {
            return ApiResponse.error("Login failed: " + e.getMessage());
        }
    }
    
    public ApiResponse<User> getUserByCustomerId(String customerId) {
        try {
            Optional<User> userOptional = userRepository.findByCustomerId(customerId);
            
            if (userOptional.isEmpty()) {
                return ApiResponse.error("User not found");
            }
            
            User user = userOptional.get();
            // Don't return password
            user.setPassword(null);
            
            return ApiResponse.success("User found", user);
            
        } catch (Exception e) {
            return ApiResponse.error("Error retrieving user: " + e.getMessage());
        }
    }
    
    public ApiResponse<Page<User>> getAllUsers(Pageable pageable) {
        try {
            Page<User> users = userRepository.findAll(pageable);
            
            // Remove passwords from response
            users.forEach(user -> user.setPassword(null));
            
            return ApiResponse.success("Users retrieved successfully", users);
            
        } catch (Exception e) {
            return ApiResponse.error("Error retrieving users: " + e.getMessage());
        }
    }
    
    public ApiResponse<Page<User>> getUsersByRole(User.Role role, Pageable pageable) {
        try {
            Page<User> users = userRepository.findByRole(role, pageable);
            
            // Remove passwords from response
            users.forEach(user -> user.setPassword(null));
            
            return ApiResponse.success("Users retrieved successfully", users);
            
        } catch (Exception e) {
            return ApiResponse.error("Error retrieving users: " + e.getMessage());
        }
    }
    
    public ApiResponse<Page<User>> searchUsers(String searchTerm, Pageable pageable) {
        try {
            Page<User> users = userRepository.findBySearchTerm(searchTerm, pageable);
            
            // Remove passwords from response
            users.forEach(user -> user.setPassword(null));
            
            return ApiResponse.success("Users retrieved successfully", users);
            
        } catch (Exception e) {
            return ApiResponse.error("Error searching users: " + e.getMessage());
        }
    }
    
    public ApiResponse<String> createOfficer(UserRegistrationDto registrationDto) {
        try {
            // Validate password confirmation
            if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
                return ApiResponse.error("Password and confirm password do not match");
            }
            
            // Check if email already exists
            if (userRepository.existsByEmail(registrationDto.getEmail())) {
                return ApiResponse.error("Email already exists");
            }
            
            // Create new officer
            User officer = new User();
            officer.setName(registrationDto.getName());
            officer.setEmail(registrationDto.getEmail());
            officer.setCountryCode(registrationDto.getCountryCode());
            officer.setMobile(registrationDto.getMobile());
            officer.setAddress(registrationDto.getAddress());
            officer.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
            officer.setRole(User.Role.OFFICER);
            officer.setNotificationPreference(registrationDto.isNotificationPreference());
            officer.setMailDeliveryPreference(registrationDto.isMailDeliveryPreference());
            
            User savedOfficer = userRepository.save(officer);
            
            return ApiResponse.success("Officer created successfully with ID: " + savedOfficer.getCustomerId());
            
        } catch (Exception e) {
            return ApiResponse.error("Officer creation failed: " + e.getMessage());
        }
    }
}