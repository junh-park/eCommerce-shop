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

import com.jun.ecommerce.domain.ProductsById;
import com.jun.ecommerce.services.ProductService;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@RestController
public class ProductController {
	private ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/products")
	public ResponseEntity<List<ProductsById>> getAllProducts(Model model) {
		List<ProductsById> products = productService.getAllProducts();
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@GetMapping("/product/{id}")
	public ResponseEntity<ProductsById> getProductById(Model model, @PathVariable UUID id) {
		ProductsById product = productService.getProductById(id);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@PostMapping("/product")
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
