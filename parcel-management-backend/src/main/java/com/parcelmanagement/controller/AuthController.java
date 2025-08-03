package com.parcelmanagement.controller;

import com.parcelmanagement.dto.ApiResponse;
import com.parcelmanagement.dto.LoginDto;
import com.parcelmanagement.dto.UserRegistrationDto;
import com.parcelmanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Map<String, Object>>> registerCustomer(
            @Valid @RequestBody UserRegistrationDto registrationDto) {
        
        ApiResponse<Map<String, Object>> response = userService.registerCustomer(registrationDto);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, Object>>> login(
            @Valid @RequestBody LoginDto loginDto) {
        
        ApiResponse<Map<String, Object>> response = userService.login(loginDto);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PostMapping("/create-officer")
    public ResponseEntity<ApiResponse<String>> createOfficer(
            @Valid @RequestBody UserRegistrationDto registrationDto) {
        
        ApiResponse<String> response = userService.createOfficer(registrationDto);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}