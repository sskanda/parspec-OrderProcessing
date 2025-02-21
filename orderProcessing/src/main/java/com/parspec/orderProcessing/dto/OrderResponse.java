package com.parspec.orderProcessing.dto;

import java.util.UUID;

public class OrderResponse {
    private String message;
    private UUID orderId;

    public OrderResponse(String message, UUID orderId) {
        this.message = message;
        this.orderId = orderId;
    }

    public String getMessage() {
        return message;
    }

    public UUID getOrderId() {
        return orderId;
    }
}
