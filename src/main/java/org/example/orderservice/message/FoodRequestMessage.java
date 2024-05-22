package org.example.orderservice.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodRequestMessage {

    private Long orderId;

    private Map<Long, Integer> foods  = new HashMap<>();

}
