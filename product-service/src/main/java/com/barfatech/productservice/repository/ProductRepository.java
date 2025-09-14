package com.barfatech.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.barfatech.productservice.model.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String>{
	// You can add custom queries here if needed
}
