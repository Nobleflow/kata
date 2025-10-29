package com.crafteam.kata.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Data Transfer Object for Client information.
 * 
 * Used to transfer client data between application layers
 * without exposing the entity directly.
 */
@Getter
@Setter
@ToString
public class ClientDto {
    /**
     * Client's first name.
     */
    private String firstName;

    /**
     * Client's last name.
     */
    private String lastName;

    /**
     * Client's delivery address.
     */
    private String address;
}
