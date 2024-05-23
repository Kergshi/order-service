package org.example.orderservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.entity.Order;
import org.example.orderservice.entity.OrderStatus;
import org.example.orderservice.mapper.OrderMapper;
import org.example.orderservice.message.FoodRequestMessage;
import org.example.orderservice.message.FoodResponseMessage;
import org.example.orderservice.repository.OrderRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private static final String FOOD_REQUEST_TOPIC = "food-request-topic";

    private static final String FOOD_RESPONSE_TOPIC = "food-response-topic";

    private static final String KAFKA_GROUP = "orders";


    private final KafkaTemplate<String, FoodRequestMessage> kafkaTemplate;

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;


    public Order insertOrder(Order order) {
        order.setCreatedAt(Instant.now());
        order.setStatus(OrderStatus.Assemble);
        orderRepository.save(order);

        FoodRequestMessage foodRequestMessage = orderMapper.orderToFoodRequest(order);

        kafkaTemplate.send(FOOD_REQUEST_TOPIC, foodRequestMessage);
        log.info(MessageFormat.format("Заказ с id {0} отравлен в FoodService",
                foodRequestMessage.getOrderId()));

        return order;
    }


    @KafkaListener(topics = FOOD_RESPONSE_TOPIC,
            groupId = KAFKA_GROUP,
            containerFactory = "foodResponseMessageConcurrentKafkaListenerContainerFactory"
    )
    private void FoodServiceListen(@Payload FoodResponseMessage foodResponseMessage) {

        Order order = orderRepository.findById(foodResponseMessage.getOrderId()).orElse(null);

        if (order == null) {
            log.error(MessageFormat.format("Заказ с id {0} отсутствует в БД",
                    foodResponseMessage.getOrderId()));
            return;
        }

        order.setAmount(foodResponseMessage.getAmount());
        order.setStatus(OrderStatus.Payment);
        orderRepository.save(order);

        // TODO add next logic to payment
    }



}
