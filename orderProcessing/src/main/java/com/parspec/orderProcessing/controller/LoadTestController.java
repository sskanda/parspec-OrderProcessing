package com.parspec.orderProcessing.controller;

import org.springframework.web.bind.annotation.*;

import com.parspec.orderProcessing.model.Order;
import com.parspec.orderProcessing.service.OrderService;

import java.util.*;
import java.util.concurrent.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/test")
public class LoadTestController {

    private final OrderService orderService;
    private final ExecutorService executorService = Executors.newFixedThreadPool(50); 

    public LoadTestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/concurrent-orders")
    public String simulateConcurrentOrders(@RequestParam(defaultValue = "10") int count) {
        List<Future<Order>> futures = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            futures.add(executorService.submit(() -> 
                orderService.createOrder(UUID.randomUUID(), "item-1,item-2", new BigDecimal("100.50"))
            ));
        }

        int successCount = 0;
        for (Future<Order> future : futures) {
            try {
                if (future.get() != null) {
                    successCount++;
                }
            } catch (Exception ignored) { }
        }

        return "Successfully created " + successCount + " orders out of " + count;
    }
}