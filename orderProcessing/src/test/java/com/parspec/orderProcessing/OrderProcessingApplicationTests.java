package com.parspec.orderProcessing;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.parspec.orderProcessing.dto.OrderRequest;
import com.parspec.orderProcessing.model.Order;
import com.parspec.orderProcessing.repository.OrderRepository;
import com.parspec.orderProcessing.service.OrderService;

@SpringBootTest
class OrderProcessingApplicationTests {
	
	@Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll(); 
    }

    @Test
    void testConcurrentOrderCreation() throws InterruptedException {
        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(50); 
        CountDownLatch latch = new CountDownLatch(threadCount); 

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    UUID userId = UUID.randomUUID();
                    OrderRequest request = new OrderRequest(userId, "item1,item2", BigDecimal.valueOf(100.0));
                    Order order = orderService.createOrder(request.getUserId(), request.getItemIds(), request.getTotalAmount());

                    // Assert that order is created and has a valid UUID
                    assertNotNull(order);
                    assertNotNull(order.getOrderId());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(); // Wait for all threads to finish
        executorService.shutdown();

        
        long orderCount = orderRepository.count();
        System.out.println("Total orders created: " + orderCount);
        assert (orderCount == threadCount);
    }

}
