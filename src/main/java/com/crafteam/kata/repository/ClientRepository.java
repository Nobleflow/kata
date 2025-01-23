package com.crafteam.kata.repository;

import com.crafteam.kata.model.Client;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ClientRepository extends JpaRepository<Client, Integer> {
}
