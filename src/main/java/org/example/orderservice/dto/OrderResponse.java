package org.example.orderservice.dto;

import lombok.*;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long id;

    private String status;

    private Map<Long, Integer> foods;

    private String address;

    private Instant createdAt;

    private Instant completedAt;

}
