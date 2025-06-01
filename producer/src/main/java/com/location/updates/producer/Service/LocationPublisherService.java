package com.location.updates.producer.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.location.updates.producer.model.DriverLocation;
import com.location.updates.producer.model.DriverTransaction;

@Service
public class LocationPublisherService {
    
    @Value("${kafka.topic.driver-location}")
    private String topic;

    @Value("${kafka.topic.driver-transaction}")
    private String transactionTopic;

    @Autowired
    @Qualifier("commonKafkaTemplate")
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    @Qualifier("exactlyOnceKafkaTemplate")
    private KafkaTemplate<String, String> exactlyOnceKafkaTemplate;

    private final ObjectMapper mapper = new ObjectMapper();

    public void publishLocation(DriverLocation location) {
        try {
            String key = location.getDriverId();
            String value = mapper.writeValueAsString(location);
            kafkaTemplate.send(topic, key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void publishTransaction(DriverTransaction transaction){ // Can we achieve transactional using annotations
        String key = transaction.getDriverId();
        String value;
        try {
                value = mapper.writeValueAsString(transaction);
                exactlyOnceKafkaTemplate.executeInTransaction(txn-> {
                txn.send(transactionTopic, key,value);
                return true;
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
       
    }
}
