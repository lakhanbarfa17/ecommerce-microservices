package com.barfatech.orderservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.barfatech.orderservice.dto.OrderLineItemsDto;
import com.barfatech.orderservice.dto.OrderRequest;
import com.barfatech.orderservice.model.Order;
import com.barfatech.orderservice.model.OrderLineItems;
import com.barfatech.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
	
	private final OrderRepository orderRepository;
	
	
	public void placeOrder(OrderRequest orderRequest) {
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		
		List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDto()
			.stream()
			.map(this::mapToDto)
			.toList();
		
		order.setOrderLineItems(orderLineItems);
		
		orderRepository.save(order);		
		
	}

	private OrderLineItems mapToDto(OrderLineItemsDto oderLineItumsDto) {
		OrderLineItems orderLineItems = new OrderLineItems();
		orderLineItems.setPrice(oderLineItumsDto.getPrice());
		orderLineItems.setQuantity(oderLineItumsDto.getQuantity());
		orderLineItems.setSkuCode(oderLineItumsDto.getSkuCode());
		return orderLineItems;
	}
}
