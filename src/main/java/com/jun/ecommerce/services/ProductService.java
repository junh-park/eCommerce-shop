package com.jun.ecommerce.services;

import java.util.List;
import java.util.UUID;

import com.jun.ecommerce.domain.Product;

public interface ProductService {

	List<Product> getAllProducts();

	Product getProductById(UUID id);

	Product addProduct(Product product);

	void deleteProduct(UUID id);

}
