package com.crafteam.kata.repository;

import com.crafteam.kata.model.Product;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product,Integer> {
}
