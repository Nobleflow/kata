package com.crafteam.kata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Main application class for the Kata Spring Boot application.
 * 
 * This application provides an order management system with support for
 * multiple delivery types, reactive endpoints, and event-driven architecture
 * using Kafka. Includes features for time slot management, HATEOAS support,
 * and real-time order streaming.
 */
@SpringBootApplication
// @EnableCaching  // Uncomment to enable caching support
public class KataApplication {

    /**
     * Application entry point.
     * 
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(KataApplication.class, args);
    }
}
