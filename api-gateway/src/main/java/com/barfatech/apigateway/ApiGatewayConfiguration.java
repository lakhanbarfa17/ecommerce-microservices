package com.barfatech.apigateway;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {
	
	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p.path("/get")
						.filters(f -> f
								.addRequestHeader("MyHeader", "MyURI")
								.addRequestParameter("Param", "MyValue")
								)
						.uri("http://httpbin.org:80"))
				
				.route(p -> p.path("/api/product")
						.uri("lb://PRODUCT-SERVICE"))
				
				.route(p -> p.path("/api/order")
						.uri("lb://ORDER-SERVICE"))
				
				.route(p -> p.path("/api/inventory")
						.uri("lb://INVENTORY-SERVICE"))
				
				
				.route(p -> p.path("/product")
						.filters(f -> f.rewritePath("/product", "/api/product"))
						.uri("lb://PRODUCT-SERVICE"))
				
				
				.build();
	}
}
