package com.jun.ecommerce.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jun.ecommerce.domain.ProductsById;
import com.jun.ecommerce.services.ProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ProductController {
	private final ProductService productService;

	@GetMapping("/products")
	public ResponseEntity<List<ProductsById>> getAllProducts() {
		List<ProductsById> products = productService.getAllProducts();
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@GetMapping("/product/{id}")
	public ResponseEntity<ProductsById> getProductById(@PathVariable UUID id) {
		ProductsById product = productService.getProductById(id);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@PostMapping(value = "/product", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductsById> addNewUser(@RequestBody ProductsById newProduct) {
		ProductsById savedProduct = productService.addProduct(newProduct);
		return new ResponseEntity<>(savedProduct, HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/product/{id}")
	public ResponseEntity<ProductsById> deleteUser(@PathVariable UUID id) {
		productService.deleteProduct(id);
		return ResponseEntity.ok().build();
	}
}