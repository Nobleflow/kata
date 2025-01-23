package com.crafteam.kata.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "delivery.time-slots")
public class DeliveryTimeSlotConfig {

    private Map<String, String> timeSlots;

    public Map<String, String> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(Map<String, String> timeSlots) {
        this.timeSlots = timeSlots;
    }
}
