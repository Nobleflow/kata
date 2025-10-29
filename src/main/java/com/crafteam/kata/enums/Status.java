package com.crafteam.kata.enums;

/**
 * Enum representing the possible statuses of an order.
 * 
 * Tracks the lifecycle of an order from creation to completion.
 */
public enum Status {
    /**
     * Order has been created and is awaiting processing/delivery.
     */
    PENDING,
    
    /**
     * Order has been successfully delivered to the customer.
     */
    DELIVERED
}
