package com.parspec.orderProcessing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parspec.orderProcessing.model.Order;
import com.parspec.orderProcessing.model.OrderStatus;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByStatus(OrderStatus status);
    long countByStatus(OrderStatus status);
}
