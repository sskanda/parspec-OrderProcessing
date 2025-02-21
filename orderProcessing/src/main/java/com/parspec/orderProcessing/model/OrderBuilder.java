package com.parspec.orderProcessing.model;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderBuilder {
    private UUID userId;
    private String itemIds;
    private BigDecimal totalAmount;
    private OrderStatus status;

    public OrderBuilder userId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public OrderBuilder itemIds(String itemIds) {
        this.itemIds = itemIds;
        return this;
    }

    public OrderBuilder totalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public OrderBuilder status(OrderStatus status) {
        this.status = status;
        return this;
    }

    public Order build() {
    	
        return new Order(userId, itemIds, totalAmount, status);
    }
}
