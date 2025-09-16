package com.barfatech.orderservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.barfatech.orderservice.dto.InventoryResponse;
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
	private final WebClient webClient;

	public void placeOrder(OrderRequest orderRequest) {

		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());

		List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDto().stream().map(this::mapToDto).toList();

		order.setOrderLineItems(orderLineItems);
		
		List<String> skuCodes = order.getOrderLineItems().stream()
				.map(OrderLineItems::getSkuCode)
				.toList();

		// call inventory service and check order is insock or not
		List<InventoryResponse> inventoryResponses = webClient.get()
			.uri("http://localhost:8082/api/inventory", 
					uriBuilder -> uriBuilder.queryParam("sku-code", skuCodes).build())
			.retrieve()
			.bodyToFlux(InventoryResponse.class)
			.collectList()
			.block();
		
		
		boolean allProductsInStock = inventoryResponses.stream().allMatch(InventoryResponse::isInStock);
		
		if(allProductsInStock)
			orderRepository.save(order);
		else
			throw new IllegalArgumentException("Product is not in stock, plase try again later");

	}

	private OrderLineItems mapToDto(OrderLineItemsDto oderLineItumsDto) {
		OrderLineItems orderLineItems = new OrderLineItems();
		orderLineItems.setPrice(oderLineItumsDto.getPrice());
		orderLineItems.setQuantity(oderLineItumsDto.getQuantity());
		orderLineItems.setSkuCode(oderLineItumsDto.getSkuCode());
		return orderLineItems;
	}
}
