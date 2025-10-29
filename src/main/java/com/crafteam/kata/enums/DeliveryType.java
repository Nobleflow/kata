package com.crafteam.kata.enums;

/**
 * Enum representing the different types of delivery options available.
 * 
 * Each type has specific time constraints and handling logic:
 * - DRIVE: Customer picks up at store within configured time slots
 * - DELIVERY: Standard delivery with scheduled time slots
 * - DELIVERY_TODAY: Same-day delivery (must be ordered before 4 PM)
 * - DELIVERY_ASAP: Express delivery within 2 hours of order
 */
public enum DeliveryType {
    /**
     * Drive-through pickup at store location.
     */
    DRIVE,
    
    /**
     * Standard home delivery.
     */
    DELIVERY,
    
    /**
     * Same-day delivery (order before 4 PM).
     */
    DELIVERY_TODAY,
    
    /**
     * Express delivery as soon as possible (within 2 hours).
     */
    DELIVERY_ASAP
}
