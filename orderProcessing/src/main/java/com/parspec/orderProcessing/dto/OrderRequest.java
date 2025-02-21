package com.parspec.orderProcessing.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderRequest {
    private UUID userId;
    private String itemIds;
    private BigDecimal totalAmount;
    
    
    public OrderRequest(UUID userId, String itemIds, BigDecimal totalAmount) {
		super();
		this.userId = userId;
		this.itemIds = itemIds;
		this.totalAmount = totalAmount;
	}

	
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    
    public String getItemIds() {
        return itemIds;
    }

    public void setItemIds(String itemIds) {
        this.itemIds = itemIds;
    }

    
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}