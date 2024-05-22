package org.example.orderservice.mapper;

import org.example.orderservice.dto.CreateOrderRequest;
import org.example.orderservice.dto.OrderResponse;
import org.example.orderservice.entity.Food;
import org.example.orderservice.entity.Order;
import org.example.orderservice.message.FoodRequestMessage;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;

@Component
public class OrderMapper {

    public Order requestToOrder(CreateOrderRequest request) {

        Order order = new Order();

        //TODO  replace random to get User ID from token
        order.setUserId((new Random().nextLong(10000L)));
        order.setTotalCost(-1d);
        order.setAddress(request.getAddress());

        for (Map.Entry<Long, Integer> requestFood : request.getFoods().entrySet()) {
            Food food = new Food();
            food.setOrder(order);
            food.getId().setFoodId(requestFood.getKey());
            food.setAmount(requestFood.getValue());

            order.getFoods().add(food);
        }

        return order;
    }

    public OrderResponse orderToResponse(Order order) {
        OrderResponse orderResponse = new OrderResponse();

        orderResponse.setId(order.getId());
        orderResponse.setAddress(order.getAddress());
        orderResponse.setStatus(order.getStatus().toString());
        orderResponse.setCreatedAt(order.getCreatedAt());
        orderResponse.setCompletedAt(order.getCompletedAt());

        for (Food food : order.getFoods()) {
            orderResponse.getFoods().put(food.getId().getFoodId(), food.getAmount());
        }

        return orderResponse;
    }

    public FoodRequestMessage orderToFoodRequest(Order order) {
        FoodRequestMessage message = new FoodRequestMessage();

        message.setOrderId(order.getId());

        for (Food food : order.getFoods()) {
            message.getFoods().put(food.getId().getFoodId(), food.getAmount());
        }

        return message;
    }


}
