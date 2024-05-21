package org.example.orderservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodKey implements Serializable {

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "food_id")
    private Long foodId;

}
