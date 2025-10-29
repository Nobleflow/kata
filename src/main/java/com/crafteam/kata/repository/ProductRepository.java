package com.crafteam.kata.repository;

import com.crafteam.kata.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Product entity persistence.
 * 
 * Provides CRUD operations for Product entities through Spring Data JPA.
 */
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
