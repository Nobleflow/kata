package com.crafteam.kata.services;

import com.crafteam.kata.dto.ClientDto;
import com.crafteam.kata.dto.OrderDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service interface for managing orders.
 * 
 * Provides methods for order retrieval, creation, and querying
 * with reactive programming support using Project Reactor.
 */
public interface OrderService {

    /**
     * Retrieves all orders in the system.
     * 
     * @return a Flux of all OrderDto objects
     */
    Flux<OrderDto> getAllOrders();

    /**
     * Creates and saves a new order.
     * 
     * Validates delivery type and time slot constraints before saving.
     * For DELIVERY_ASAP orders, automatically sets delivery window.
     * For DELIVERY_TODAY orders, validates cutoff time.
     * 
     * @param orderDTO the order to save
     * @return a Mono containing the saved OrderDto
     * @throws IllegalArgumentException if validation fails
     */
    Mono<OrderDto> saveOrder(OrderDto orderDTO);

    /**
     * Retrieves all orders for a specific client.
     * 
     * @param clientDto the client to query orders for
     * @return a Flux of OrderDto objects for the specified client
     */
    Flux<OrderDto> getAllOrderByClients(ClientDto clientDto);
}
