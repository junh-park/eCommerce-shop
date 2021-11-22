package com.jun.ecommerce.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jun.ecommerce.data.ProductByCategoryRepository;
import com.jun.ecommerce.data.ProductRepository;
import com.jun.ecommerce.domain.ProductsById;
import com.jun.ecommerce.domain.ProductsByCategory;
import com.jun.ecommerce.exceptions.ResourceNotFoundException;

@Service
public class ProductServiceImpl implements ProductService {
	private ProductRepository productRepo;
	private ProductByCategoryRepository productByCategoryRepo;

	public ProductServiceImpl(ProductRepository productRepo, ProductByCategoryRepository productByCategoryRepo) {
		this.productRepo = productRepo;
		this.productByCategoryRepo = productByCategoryRepo;
	}

	public List<ProductsById> getAllProducts() {
		return null;
	}

	public ProductsById getProductById(UUID id) {
		return null;
	}

	public ProductsById addProduct(ProductsById product) {
		return null;
	}

	public void deleteProduct(UUID id) {
		
	}

	public List<ProductsByCategory> getAllProductsByCategory(String category) {
		return productByCategoryRepo.findByCategory(category)
				.orElseThrow(ResourceNotFoundException::new);
	}

	public List<ProductsByCategory> updateProduct(ProductsById product) {
		
		return null;
	}
}
