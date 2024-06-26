package org.example.orderservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.orderservice.message.FoodRequestMessage;
import org.example.orderservice.message.FoodResponseMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaFoodServiceConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ProducerFactory<String, FoodRequestMessage> foodRequestMessageProducerFactory
            (ObjectMapper objectMapper) {

        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(config, new StringSerializer(),
                new JsonSerializer<>(objectMapper));

    }

    @Bean
    public KafkaTemplate<String, FoodRequestMessage> kafkaTemplate(
            ProducerFactory<String, FoodRequestMessage> foodRequestMessageProducerFactory) {

        return new KafkaTemplate<>(foodRequestMessageProducerFactory);
    }

    @Bean
    public ConsumerFactory<String, FoodResponseMessage> foodResponseMessageConsumerFactory(
            ObjectMapper objectMapper) {

        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
                new JsonDeserializer<>(objectMapper));

    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, FoodResponseMessage>
        foodResponseMessageConcurrentKafkaListenerContainerFactory(
            ConsumerFactory<String, FoodResponseMessage> foodResponseMessageConsumerFactory) {

        ConcurrentKafkaListenerContainerFactory<String, FoodResponseMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(foodResponseMessageConsumerFactory);

        return factory;
    }


}
