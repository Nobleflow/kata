package com.crafteam.kata.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Configuration class for delivery time slots.
 * 
 * Loads time slot configurations from application.yaml under the
 * 'delivery.time-slots' prefix. Each delivery type can have multiple
 * time slots defined as comma-separated values.
 * 
 * Example configuration:
 * delivery:
 *   time-slots:
 *     DRIVE: 09:00-10:00,10:00-11:00
 *     DELIVERY: 14:00-15:00,15:00-16:00
 */
@Component
@ConfigurationProperties(prefix = "delivery.time-slots")
public class DeliveryTimeSlotConfig {

    /**
     * Map of delivery types to their available time slots.
     * Key: Delivery type name (e.g., "DRIVE", "DELIVERY")
     * Value: Comma-separated time slots (e.g., "09:00-10:00,10:00-11:00")
     */
    private Map<String, String> timeSlots;

    /**
     * Gets the time slots configuration map.
     * 
     * @return map of delivery types to time slots
     */
    public Map<String, String> getTimeSlots() {
        return timeSlots;
    }

    /**
     * Sets the time slots configuration map.
     * 
     * @param timeSlots map of delivery types to time slots
     */
    public void setTimeSlots(Map<String, String> timeSlots) {
        this.timeSlots = timeSlots;
    }
}
