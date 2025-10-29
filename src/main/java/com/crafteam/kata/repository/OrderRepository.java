package com.crafteam.kata.repository;

import com.crafteam.kata.model.Client;
import com.crafteam.kata.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for Order entity persistence.
 * 
 * Provides CRUD operations and custom query methods for Order entities.
 * Extends JpaRepository to leverage Spring Data JPA functionality.
 */
public interface OrderRepository extends JpaRepository<Order, Integer> {

    /**
     * Finds all orders placed by a specific client.
     * 
     * @param client the client to search orders for
     * @return list of orders associated with the client
     */
    List<Order> findByClient(Client client);
}
