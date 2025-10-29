package com.crafteam.kata.repository;

import com.crafteam.kata.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Client entity persistence.
 * 
 * Provides CRUD operations for Client entities through Spring Data JPA.
 */
public interface ClientRepository extends JpaRepository<Client, Integer> {
}
