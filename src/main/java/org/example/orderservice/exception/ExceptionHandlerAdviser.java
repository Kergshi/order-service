package org.example.orderservice.exception;

import org.example.orderservice.dto.OrderErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdviser {

    @ExceptionHandler(OrderServiceException.class)
    public ResponseEntity<OrderErrorResponse> handle(OrderServiceException ex){

        return ResponseEntity
                .status(ex.getStatus())
                .body(new OrderErrorResponse(ex.getLocalizedMessage()));
    }

}
