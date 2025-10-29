package com.crafteam.kata.enums;

import com.crafteam.kata.conf.DeliveryTimeSlotConfig;

import java.util.List;

/**
 * Enum for delivery time slot validation and retrieval.
 * 
 * Provides utility methods to validate and retrieve available time slots
 * for different delivery types based on configuration.
 * 
 * Note: This enum duplicates DeliveryType values. Consider consolidating
 * or removing if not needed separately.
 */
public enum DeliveryTimeSlot {
    /**
     * Drive-through pickup time slots.
     */
    DRIVE,
    
    /**
     * Standard delivery time slots.
     */
    DELIVERY,
    
    /**
     * Same-day delivery time slots.
     */
    DELIVERY_TODAY,
    
    /**
     * Express ASAP delivery (no specific slots).
     */
    DELIVERY_ASAP;

    /**
     * Retrieves available time slots for a given delivery type.
     * 
     * @param deliveryType the delivery type to get slots for
     * @param config the configuration containing time slot definitions
     * @return list of available time slots
     * @throws IllegalArgumentException if delivery type is not configured
     */
    public static List<String> getTimeSlots(String deliveryType, DeliveryTimeSlotConfig config) {
        String timeSlotString = config.getTimeSlots().get(deliveryType);
        if (timeSlotString == null) {
            throw new IllegalArgumentException("Invalid delivery type: " + deliveryType);
        }
        return List.of(timeSlotString.split(","));
    }
}