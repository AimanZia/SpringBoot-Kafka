package com.location.updates.consumer.Service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class LocationConsumerService {
    
    @KafkaListener(topics = "test1-driver-location-updates", groupId = "t1-at-most-once", containerFactory = "atMostOnceConsumerFactory")
    public void atMostOnceConsumer(String message) {
        // Auto-commit offset before processing
        System.out.println("At Most Conssumer Group");
        System.out.println(message);
    }

    @KafkaListener(topics = "test1-driver-location-updates", groupId = "t1-at-least-once", containerFactory = "atLeastOnceConsumerFactory")
    public void atLeastOnceConsumer(String message, Acknowledgment ack) {
        // Process then manually commit
        System.out.println("At Least Once Conssumer Group");
        System.out.println(message);
        ack.acknowledge();
    }

    @KafkaListener(topics = "test1-driver-transaction-updates", groupId = "t1-exactly-once", containerFactory = "exactlyOnceConsumerFactory")
    public void exactlyOnceConsumer(String message, Acknowledgment ack) {
        // Transactional commit handling
        System.out.println("Exactly Once Conssumer Group");
        System.out.println(message);
        ack.acknowledge();
    }
}
