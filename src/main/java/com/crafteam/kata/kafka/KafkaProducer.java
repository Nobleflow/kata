package com.crafteam.kata.kafka;

import com.crafteam.kata.dto.OrderDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Kafka producer service for publishing order events.
 * 
 * Sends order messages to the Kafka "orders" topic for event-driven
 * processing and integration with other systems.
 */
@Service
public class KafkaProducer {
    
    private final KafkaTemplate<String, OrderDto> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, OrderDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Sends an order to the Kafka "orders" topic.
     * 
     * @param order the OrderDto to publish to Kafka
     */
    public void sendOrder(OrderDto order) {
        kafkaTemplate.send("orders", order);
    }
}