package org.example.orderservice.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodRequestMessage {

    private String token;

    private Long orderId;

    private Map<Long, Integer> foods;

}
