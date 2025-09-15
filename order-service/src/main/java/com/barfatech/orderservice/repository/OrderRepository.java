package com.barfatech.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barfatech.orderservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
