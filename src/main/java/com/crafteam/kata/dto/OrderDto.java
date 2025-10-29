package com.crafteam.kata.dto;

import com.crafteam.kata.enums.DeliveryType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object for Order information.
 * 
 * Used to transfer order data between application layers
 * without exposing the entity directly. Contains all order details
 * including products, delivery information, and status.
 */
@Getter
@Setter
@ToString
public class OrderDto {
    /**
     * Unique identifier for the order.
     */
    private Long id;

    /**
     * List of products included in this order.
     */
    private List<ProductDto> products;

    /**
     * Type of delivery requested (DRIVE, DELIVERY, DELIVERY_TODAY, DELIVERY_ASAP).
     */
    private DeliveryType deliveryType;

    /**
     * Information about the client who placed the order.
     */
    private ClientDto client;

    /**
     * Timestamp when the order was placed.
     */
    private LocalDateTime orderAt;

    /**
     * Start time of the delivery window.
     */
    private LocalDateTime dateBeginOrder;

    /**
     * End time of the delivery window.
     */
    private LocalDateTime dateEndOrder;

    /**
     * Current status of the order (e.g., PENDING, DELIVERED).
     */
    private String status;
    
    /**
     * Expected delivery date for the order.
     */
    private LocalDateTime deliveryDate;
    
    /**
     * Selected time slot for delivery (e.g., "09:00-10:00").
     */
    private String timeSlot;
    
    /**
     * Indicates whether a delivery slot has been reserved.
     */
    private boolean isReserved;
}
