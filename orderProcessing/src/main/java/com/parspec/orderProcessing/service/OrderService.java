package com.parspec.orderProcessing.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.parspec.orderProcessing.exception.OrderNotFoundException;
import com.parspec.orderProcessing.model.Order;
import com.parspec.orderProcessing.model.OrderBuilder;
import com.parspec.orderProcessing.model.OrderStatus;
import com.parspec.orderProcessing.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MetricsService metricsService;
    private final BlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();
    Random random = new Random();
    
    public OrderService(OrderRepository orderRepository,MetricsService metricsService) {
        this.orderRepository = orderRepository;
        this.metricsService = metricsService;
        Thread thread = new Thread(this::processOrders);
        thread.setDaemon(true); 
        thread.start();
    }


    public Order createOrder(UUID userId, String itemIds, BigDecimal totalAmount) {
        Order order = new OrderBuilder()
                .userId(userId)
                .itemIds(itemIds)
                .totalAmount(totalAmount)
                .status(OrderStatus.PENDING)
                .build();
        order = orderRepository.save(order);
        
        orderQueue.offer(order);
        return order;
    }

    public Order getOrderStatus(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found"));
    }

    
    public void processOrders() {
        while (true) {
            try {
                Order order = orderQueue.take(); 
                
                long startTime = System.currentTimeMillis();
                
                order.setStatus(OrderStatus.PROCESSING);
                orderRepository.save(order);
                

                int processingTime = random.nextInt(10) + 1; 
                Thread.sleep(processingTime * 1000L);

                order.setStatus(OrderStatus.COMPLETED);
                orderRepository.save(order);
                
                long endTime = System.currentTimeMillis();
                double actualProcessingTime = (endTime - startTime) / 1000.0; 
                
                metricsService.updateProcessingTime(processingTime);
                
                System.out.println("Order ID: " + order.getOrderId() + " processed in " + actualProcessingTime + " seconds");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Order processing interrupted", e);
            }
        }
    }
}
