package com.parspec.orderProcessing.controller;


import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.parspec.orderProcessing.dto.OrderRequest;
import com.parspec.orderProcessing.dto.OrderResponse;
import com.parspec.orderProcessing.model.Order;
import com.parspec.orderProcessing.service.OrderService;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping
    public ResponseEntity<OrderResponse>  createOrder(@RequestBody OrderRequest orderRequest) {
    	 Order order = orderService.createOrder(orderRequest.getUserId(),
                orderRequest.getItemIds(),
                orderRequest.getTotalAmount());
    	 OrderResponse response = new OrderResponse("Order placed successfully", order.getOrderId());
    	 return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderStatus(@PathVariable UUID orderId) {
        Order order = orderService.getOrderStatus(orderId);
        System.out.println(order.getStatus());
        return ResponseEntity.ok(order);
    }
}
