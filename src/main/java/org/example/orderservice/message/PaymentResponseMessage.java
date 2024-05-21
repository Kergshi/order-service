package org.example.orderservice.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseMessage {

    private String token;

    private Long orderId;

    private Boolean completed;

}
