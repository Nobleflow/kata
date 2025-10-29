package com.crafteam.kata.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Data Transfer Object for Product information.
 * 
 * Used to transfer product data between application layers
 * without exposing the entity directly.
 */
@Getter
@Setter
@ToString
public class ProductDto {
    /**
     * Product name/label.
     */
    private String label;
}
