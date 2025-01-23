package com.crafteam.kata.kafka;

import com.crafteam.kata.dto.OrderDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    private final KafkaTemplate<String, OrderDto> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, OrderDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrder(OrderDto order) {
        kafkaTemplate.send("orders", order);
    }
}