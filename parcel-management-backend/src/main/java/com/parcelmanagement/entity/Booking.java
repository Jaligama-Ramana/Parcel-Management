package com.parcelmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String bookingId; // Auto-generated booking ID
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "officer_id")
    private User officer; // Null if booked by customer directly
    
    // Receiver Information
    @NotBlank(message = "Receiver name is required")
    @Column(nullable = false)
    private String receiverName;
    
    @NotBlank(message = "Receiver address is required")
    @Column(nullable = false, length = 500)
    private String receiverAddress;
    
    @NotBlank(message = "Receiver pin is required")
    @Column(nullable = false)
    private String receiverPin;
    
    @NotBlank(message = "Receiver mobile is required")
    @Column(nullable = false)
    private String receiverMobile;
    
    // Parcel Information
    @NotNull(message = "Parcel weight is required")
    @Positive(message = "Parcel weight must be positive")
    @Column(nullable = false)
    private Double parcelWeight;
    
    @NotBlank(message = "Contents description is required")
    @Column(nullable = false, length = 500)
    private String contentsDescription;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryType deliveryType;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PackingPreference packingPreference;
    
    // Timing
    private LocalDateTime pickupTime;
    private LocalDateTime dropoffTime;
    
    // Cost Calculation
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal baseRate = BigDecimal.valueOf(50.0);
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal weightCharge;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal deliveryCharge;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal packingCharge;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal adminFee = BigDecimal.ZERO;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal taxRate = BigDecimal.valueOf(0.05); // 5%
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalCost;
    
    // Status and Tracking
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status = BookingStatus.NEW;
    
    @Column(name = "booking_date", nullable = false)
    private LocalDateTime bookingDate;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Enums
    public enum DeliveryType {
        STANDARD(30.0), EXPRESS(80.0), SAME_DAY(150.0);
        
        private final double charge;
        
        DeliveryType(double charge) {
            this.charge = charge;
        }
        
        public double getCharge() {
            return charge;
        }
    }
    
    public enum PackingPreference {
        BASIC(10.0), PREMIUM(30.0);
        
        private final double charge;
        
        PackingPreference(double charge) {
            this.charge = charge;
        }
        
        public double getCharge() {
            return charge;
        }
    }
    
    public enum BookingStatus {
        NEW, SCHEDULED, PICKED_UP, ASSIGNED, BOOKED, IN_TRANSIT, DELIVERED, CANCELLED
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        bookingDate = LocalDateTime.now();
        if (bookingId == null) {
            bookingId = generateBookingId();
        }
        calculateTotalCost();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    private String generateBookingId() {
        return "BK" + System.currentTimeMillis();
    }
    
    public void calculateTotalCost() {
        // WeightCharge = 0.02 * weight
        this.weightCharge = BigDecimal.valueOf(parcelWeight * 0.02);
        
        // DeliveryCharge based on delivery type
        this.deliveryCharge = BigDecimal.valueOf(deliveryType.getCharge());
        
        // PackingCharge based on packing preference
        this.packingCharge = BigDecimal.valueOf(packingPreference.getCharge());
        
        // Calculate subtotal
        BigDecimal subtotal = baseRate
            .add(weightCharge)
            .add(deliveryCharge)
            .add(packingCharge)
            .add(adminFee);
        
        // Apply tax
        BigDecimal tax = subtotal.multiply(taxRate);
        this.totalCost = subtotal.add(tax);
    }
    
    // Constructors
    public Booking() {}
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getBookingId() {
        return bookingId;
    }
    
    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }
    
    public User getCustomer() {
        return customer;
    }
    
    public void setCustomer(User customer) {
        this.customer = customer;
    }
    
    public User getOfficer() {
        return officer;
    }
    
    public void setOfficer(User officer) {
        this.officer = officer;
    }
    
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
        if (parcelWeight != null) {
            calculateTotalCost();
        }
    }
    
    public String getContentsDescription() {
        return contentsDescription;
    }
    
    public void setContentsDescription(String contentsDescription) {
        this.contentsDescription = contentsDescription;
    }
    
    public DeliveryType getDeliveryType() {
        return deliveryType;
    }
    
    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
        if (deliveryType != null) {
            calculateTotalCost();
        }
    }
    
    public PackingPreference getPackingPreference() {
        return packingPreference;
    }
    
    public void setPackingPreference(PackingPreference packingPreference) {
        this.packingPreference = packingPreference;
        if (packingPreference != null) {
            calculateTotalCost();
        }
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
    
    public BigDecimal getBaseRate() {
        return baseRate;
    }
    
    public void setBaseRate(BigDecimal baseRate) {
        this.baseRate = baseRate;
    }
    
    public BigDecimal getWeightCharge() {
        return weightCharge;
    }
    
    public void setWeightCharge(BigDecimal weightCharge) {
        this.weightCharge = weightCharge;
    }
    
    public BigDecimal getDeliveryCharge() {
        return deliveryCharge;
    }
    
    public void setDeliveryCharge(BigDecimal deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }
    
    public BigDecimal getPackingCharge() {
        return packingCharge;
    }
    
    public void setPackingCharge(BigDecimal packingCharge) {
        this.packingCharge = packingCharge;
    }
    
    public BigDecimal getAdminFee() {
        return adminFee;
    }
    
    public void setAdminFee(BigDecimal adminFee) {
        this.adminFee = adminFee;
        calculateTotalCost();
    }
    
    public BigDecimal getTaxRate() {
        return taxRate;
    }
    
    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }
    
    public BigDecimal getTotalCost() {
        return totalCost;
    }
    
    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }
    
    public BookingStatus getStatus() {
        return status;
    }
    
    public void setStatus(BookingStatus status) {
        this.status = status;
    }
    
    public LocalDateTime getBookingDate() {
        return bookingDate;
    }
    
    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}