package com.barfatech.productservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.barfatech.productservice.dto.ProductRequest;
import com.barfatech.productservice.dto.ProductResponse;
import com.barfatech.productservice.model.Product;
import com.barfatech.productservice.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	
	private final ProductRepository productRepository;
	
	public void createProduct(ProductRequest productRequest) {
		Product product = Product.builder()
				.name(productRequest.getName())
				.description(productRequest.getDescription())
				.price(productRequest.getPrice())
				.build();
		
		productRepository.save(product);
	}

	public List<ProductResponse> getAllProducts() {
		List<Product>  products = productRepository.findAll();
		
		return products.stream().map(this::mapToProductResponse).toList();
				
	}
	
	private ProductResponse mapToProductResponse(Product product) {
		return ProductResponse.builder()
				.id(product.getId())
				.name(product.getName())
				.description(product.getDescription())
				.price(product.getPrice())
				.build();
	}
}
