package com.jun.ecommerce.services;

import java.util.List;
import java.util.UUID;

import com.jun.ecommerce.domain.ProductsById;
import com.jun.ecommerce.domain.ProductsByCategory;

public interface ProductService {

	List<ProductsById> getAllProducts();

	ProductsById getProductById(UUID id);

	ProductsById addProduct(ProductsById product);

	void deleteProduct(UUID id);

	List<ProductsByCategory> getAllProductsByCategory(String category);

}
