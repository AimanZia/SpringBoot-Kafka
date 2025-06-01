package com.location.updates.producer.Config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;


@Configuration
public class KafkaProducerConfig {
    
    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ProducerFactory<String,String> commonProducerFactory(){
        Map<String,Object> config = baseProducersProp();
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, String> commonKafkaTemplate() {
        return new KafkaTemplate<>(commonProducerFactory());
    }

    @Bean
    public ProducerFactory<String,String> exactlyOnceProducerFactory(){
       Map<String,Object> config = baseProducersProp();
       //config.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "txn-exactly-once");  // t uniquely identifies the producer in Kafka.
      //Kafka tracks this ID internally to detect uncommitted or unfinished transactions.
       DefaultKafkaProducerFactory<String, String> factory = new DefaultKafkaProducerFactory<>(config);
       //The transactional.id must be unique per producer instance.
       factory.setTransactionIdPrefix("txn-");
       return factory;
    }

    @Bean
    public KafkaTemplate<String, String> exactlyOnceKafkaTemplate() {
        return new KafkaTemplate<>(exactlyOnceProducerFactory());
    }

    private Map<String,Object> baseProducersProp(){
        Map<String,Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.ACKS_CONFIG, "all");
        return config;
    }
}
