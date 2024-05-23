package org.example.orderservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_foods")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Food {

    @EmbeddedId
    private FoodKey id = new FoodKey();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "amount")
    private Integer count;


}
