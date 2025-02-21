package com.parspec.orderProcessing.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter 
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Generates a UUID for the order_id
    @Column(name = "order_id", updatable = false, nullable = false)
    private UUID orderId;


    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "item_ids", nullable = false)
    private String itemIds; 

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    
    public Order() {
    	
    }
    
    public Order(UUID userId, String itemIds, BigDecimal totalAmount, OrderStatus status) {
        this.userId = userId;
        this.itemIds = itemIds;
        this.totalAmount = totalAmount;
        this.status = status;
    }
    
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    public UUID getOrderId() {
        return orderId;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getItemIds() {
        return itemIds;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    
    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setItemIds(String itemIds) {
        this.itemIds = itemIds;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

}