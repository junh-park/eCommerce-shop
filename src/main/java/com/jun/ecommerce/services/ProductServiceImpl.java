package com.jun.ecommerce.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jun.ecommerce.data.ProductsByCategoryRepository;
import com.jun.ecommerce.data.ProductByIdRepository;
import com.jun.ecommerce.domain.ProductsById;
import com.jun.ecommerce.domain.ProductsByCart;
import com.jun.ecommerce.domain.ProductsByCategory;
import com.jun.ecommerce.exceptions.ResourceAlreadyExistsException;
import com.jun.ecommerce.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
	private final ProductByIdRepository productByIdRepo;
	private final ProductsByCategoryRepository productByCategoryRepo;

	public List<ProductsById> getAllProducts() {
		return productByIdRepo.findAll();
	}
	
	public ProductsById getProductById(UUID id) {
		return productByIdRepo.findById(id)
				.orElseThrow(ResourceNotFoundException::new);	}

	public ProductsById addProduct(ProductsById product) {
		Optional<ProductsById> optProduct = productByIdRepo.findById(product.getId());
		
		if(optProduct.isEmpty()) {
			ProductsById newProduct = new ProductsById(product.getName(), product.getCategory(), product.getManufacturer(),
					product.getPrice(), product.getDesc(), product.getImageUrl());
			ProductsByCategory newProductByCategory = idToCategoryMapper(newProduct);
			productByCategoryRepo.save(newProductByCategory);
			return productByIdRepo.save(product);
		} else {
			throw new ResourceAlreadyExistsException();
		}
	}

	public void deleteProduct(UUID id) {
		Optional<ProductsById> optProduct = productByIdRepo.findById(id);
		
		if(optProduct.isPresent()) {
			ProductsByCategory newProduct = new ProductsByCategory();
			newProduct.setId(optProduct.get().getId());
			newProduct.setCategory(optProduct.get().getCategory());

			productByCategoryRepo.delete(newProduct);
			productByIdRepo.deleteById(optProduct.get().getId());
		} else {
			throw new ResourceNotFoundException();
		}
	}

	public List<ProductsByCategory> getProductsByCategory(String category) {
		return productByCategoryRepo.findByCategory(category)
				.orElseThrow(ResourceNotFoundException::new);
	}

	public ProductsByCategory updateProduct(ProductsByCategory product) {
		Optional<ProductsByCategory> returned = productByCategoryRepo
				.findByIdAndCategory(product.getId(), product.getCategory());
		
		if(returned.isPresent()) {
			productByIdRepo.save(categoryToIdMapper(product));
			return productByCategoryRepo.save(product);
		} else {
			throw new ResourceNotFoundException();
		}
	}
	
	public ProductsById updateProduct(ProductsById product) {
		Optional<ProductsById> returned = productByIdRepo.findById(product.getId());
		
		if(returned.isPresent()) {
			productByCategoryRepo.save(idToCategoryMapper(product));
			return productByIdRepo.save(product);
		} else {
			throw new ResourceNotFoundException();
		}
	}
	
	private ProductsByCategory idToCategoryMapper(ProductsById product) {
		ProductsByCategory newProduct = new ProductsByCategory(product.getName(), product.getCategory(), product.getManufacturer(),
				product.getPrice(), product.getDesc(), product.getImageUrl());
		newProduct.setId(product.getId());
		return newProduct;
	}
	
	private ProductsById categoryToIdMapper(ProductsByCategory product) {
		ProductsById newProduct = new ProductsById(product.getName(), product.getCategory(), product.getManufacturer(),
				product.getPrice(), product.getDesc(), product.getImageUrl());
		newProduct.setId(product.getId());
		return newProduct;
	}

}
