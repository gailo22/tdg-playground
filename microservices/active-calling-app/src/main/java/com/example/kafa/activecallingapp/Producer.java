package com.example.kafa.activecallingapp;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Producer { // TODO: This class is for testing purpose

    private final KafkaTemplate<String, String> kafkaTemplate;

    @EventListener(ApplicationStartedEvent.class)
    public void produce() {
        kafkaTemplate.send("active-calling", "100", "true");
        kafkaTemplate.send("active-calling", "200", "true");
        kafkaTemplate.send("active-calling", "100", "false");
        kafkaTemplate.send("active-calling", "300", "true");
    }
}
