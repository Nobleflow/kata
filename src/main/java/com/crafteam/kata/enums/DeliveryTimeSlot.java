package com.crafteam.kata.enums;
import com.crafteam.kata.conf.DeliveryTimeSlotConfig;

import java.util.List;

public enum DeliveryTimeSlot {

    DRIVE, DELIVERY, DELIVERY_TODAY, DELIVERY_ASAP;

    public static List<String> getTimeSlots(String deliveryType, DeliveryTimeSlotConfig config) {
        String timeSlotString = config.getTimeSlots().get(deliveryType);
        if (timeSlotString == null) {
            throw new IllegalArgumentException("Invalid delivery type: " + deliveryType);
        }
        return List.of(timeSlotString.split(","));
    }
}