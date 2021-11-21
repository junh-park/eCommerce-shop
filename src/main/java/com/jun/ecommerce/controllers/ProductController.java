package com.jun.ecommerce.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jun.ecommerce.domain.Product;
import com.jun.ecommerce.services.ProductService;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@RestController
public class ProductController {
	private ProductService productService;

	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts(Model model) {
		List<Product> products = productService.getAllProducts();
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@GetMapping("/product/{id}")
	public ResponseEntity<Product> getProductById(Model model, @PathVariable UUID id) {
		Product product = productService.getProductById(id);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@PostMapping("/product")
	public ResponseEntity<Product> addNewUser(@RequestBody Product newProduct) {
		Product savedProduct = productService.addProduct(newProduct);
		return new ResponseEntity<>(savedProduct, HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/product/{id}")
	public ResponseEntity deleteUser(@PathVariable UUID id) {
		productService.deleteProduct(id);
		return ResponseEntity.ok().build();
	}
}
