package com.parcelmanagement.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginDto {
    
    @NotBlank(message = "Customer ID is required")
    private String customerId;
    
    @NotBlank(message = "Password is required")
    private String password;
    
    // Constructors
    public LoginDto() {}
    
    public LoginDto(String customerId, String password) {
        this.customerId = customerId;
        this.password = password;
    }
    
    // Getters and Setters
    public String getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}