package com.parspec.orderProcessing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.parspec.orderProcessing.model.OrderStatus;
import com.parspec.orderProcessing.repository.OrderRepository;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MetricsService {
    private final OrderRepository orderRepository;
    
   
    public MetricsService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    
    private final AtomicLong totalProcessingTime = new AtomicLong(0);
    private final AtomicInteger processedOrdersCount = new AtomicInteger(0);

    public void updateProcessingTime(long processingTime) {
        totalProcessingTime.addAndGet(processingTime);
        processedOrdersCount.incrementAndGet();
    }
    
    public double getAverageProcessingTime() {
        int count = processedOrdersCount.get();
        return count == 0 ? 0 : (double) totalProcessingTime.get() / count;
    }

    public Map<String, Object> getMetrics() {
        
        Map<String, Object> metrics = new ConcurrentHashMap<>();
        metrics.put("total_orders_processed", orderRepository.count());
        metrics.put("average_processing_time", getAverageProcessingTime() + " seconds");
        metrics.put("order_status_counts", getOrderStatusCounts());
        return metrics;
    }

    private Map<OrderStatus, Long> getOrderStatusCounts() {
        Map<OrderStatus, Long> counts = new ConcurrentHashMap<>();
        for (OrderStatus status : OrderStatus.values()) {
            counts.put(status, orderRepository.countByStatus(status));
        }
        return counts;
    }
}