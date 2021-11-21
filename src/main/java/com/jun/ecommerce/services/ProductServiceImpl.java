package com.jun.ecommerce.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jun.ecommerce.data.ProductRepository;
import com.jun.ecommerce.domain.Product;

@Service
public class ProductServiceImpl implements ProductService {
	private ProductRepository productRepo;

	public ProductServiceImpl(ProductRepository productRepo) {
		this.productRepo = productRepo;
	}

	public List<Product> getAllProducts() {
		return null;
	}

	@Override
	public Product getProductById(UUID id) {
		return null;
	}

	@Override
	public Product addProduct(Product product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteProduct(UUID id) {
		// TODO Auto-generated method stub
		
	}

}
