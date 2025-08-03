package com.parcelmanagement.dto;

import com.parcelmanagement.entity.Booking;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BookingDto {
    
    // Receiver Information
    @NotBlank(message = "Receiver name is required")
    private String receiverName;
    
    @NotBlank(message = "Receiver address is required")
    private String receiverAddress;
    
    @NotBlank(message = "Receiver pin is required")
    private String receiverPin;
    
    @NotBlank(message = "Receiver mobile is required")
    private String receiverMobile;
    
    // Parcel Information
    @NotNull(message = "Parcel weight is required")
    @Positive(message = "Parcel weight must be positive")
    private Double parcelWeight;
    
    @NotBlank(message = "Contents description is required")
    private String contentsDescription;
    
    @NotNull(message = "Delivery type is required")
    private Booking.DeliveryType deliveryType;
    
    @NotNull(message = "Packing preference is required")
    private Booking.PackingPreference packingPreference;
    
    // Timing
    private LocalDateTime pickupTime;
    private LocalDateTime dropoffTime;
    
    // Cost (calculated automatically)
    private BigDecimal totalCost;
    
    // Customer ID (for officer bookings)
    private String customerId;
    
    // Constructors
    public BookingDto() {}
    
    // Getters and Setters
    public String getReceiverName() {
        return receiverName;
    }
    
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }
    
    public String getReceiverAddress() {
        return receiverAddress;
    }
    
    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }
    
    public String getReceiverPin() {
        return receiverPin;
    }
    
    public void setReceiverPin(String receiverPin) {
        this.receiverPin = receiverPin;
    }
    
    public String getReceiverMobile() {
        return receiverMobile;
    }
    
    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }
    
    public Double getParcelWeight() {
        return parcelWeight;
    }
    
    public void setParcelWeight(Double parcelWeight) {
        this.parcelWeight = parcelWeight;
    }
    
    public String getContentsDescription() {
        return contentsDescription;
    }
    
    public void setContentsDescription(String contentsDescription) {
        this.contentsDescription = contentsDescription;
    }
    
    public Booking.DeliveryType getDeliveryType() {
        return deliveryType;
    }
    
    public void setDeliveryType(Booking.DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }
    
    public Booking.PackingPreference getPackingPreference() {
        return packingPreference;
    }
    
    public void setPackingPreference(Booking.PackingPreference packingPreference) {
        this.packingPreference = packingPreference;
    }
    
    public LocalDateTime getPickupTime() {
        return pickupTime;
    }
    
    public void setPickupTime(LocalDateTime pickupTime) {
        this.pickupTime = pickupTime;
    }
    
    public LocalDateTime getDropoffTime() {
        return dropoffTime;
    }
    
    public void setDropoffTime(LocalDateTime dropoffTime) {
        this.dropoffTime = dropoffTime;
    }
    
    public BigDecimal getTotalCost() {
        return totalCost;
    }
    
    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }
    
    public String getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}