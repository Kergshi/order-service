package org.example.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.dto.CreateOrderRequest;
import org.example.orderservice.dto.OrderResponse;
import org.example.orderservice.entity.Food;
import org.example.orderservice.entity.FoodKey;
import org.example.orderservice.entity.Order;
import org.example.orderservice.entity.OrderStatus;
import org.example.orderservice.repository.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest request) {

        Order order = new Order();

        order.setCreatedAt(Instant.now());
        order.setAddress(request.getAddress());
        order.setStatus(OrderStatus.Assemble);
        order.setUserId(2L);
        order.setTotalCost(1200d);

        Food food = new Food();
        food.setOrder(order);
        food.setId(new FoodKey());
        food.getId().setFoodId(3L);
        food.setAmount(3);


        order.getFoods().add(food);



        orderRepository.save(order);

        return ResponseEntity.notFound().build();
    }


}
