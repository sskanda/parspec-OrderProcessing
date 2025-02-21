package com.parspec.orderProcessing.exception;

public class OrderNotFoundException extends RuntimeException{
	public OrderNotFoundException(String message) {
        super(message);
    }
}
