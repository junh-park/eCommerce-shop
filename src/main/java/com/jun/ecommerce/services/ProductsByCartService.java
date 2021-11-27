package com.jun.ecommerce.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jun.ecommerce.data.ProductsByCartRepository;
import com.jun.ecommerce.domain.ProductsByCart;
import com.jun.ecommerce.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductsByCartService {
	private final ProductsByCartRepository repo;

	public double getSubtotalFor(UUID cartId, UUID productId) {
		Optional<ProductsByCart> optProduct = repo.findByCartIdAndProductId(productId, productId);
		if (optProduct.isPresent()) {
			return optProduct.get().getSubtotal();
		} else {
			throw new ResourceNotFoundException();
		}
	}

	public ProductsByCart addNewProductToCart(ProductsByCart cart) {
		cart.setSubtotal(cart.getPrice() * cart.getQuantity());
		return repo.save(cart);
	}

	public ProductsByCart getExistingProduct(ProductsByCart cart) {
		Optional<ProductsByCart> optProduct = repo.findByCartIdAndProductId(cart.getCartId(), cart.getProductId());
		if (optProduct.isPresent()) {
			return optProduct.get();
		} else {
			throw new ResourceNotFoundException();
		}
	}

	public ProductsByCart updateQuantity(ProductsByCart cart) {
		Optional<ProductsByCart> optProduct = repo.findByCartIdAndProductId(cart.getCartId(), cart.getProductId());
		optProduct.ifPresent((product) -> {
			product.setQuantity(cart.getQuantity());
			product.setSubtotal(cart.getQuantity()* cart.getPrice());
		});
	
	}

}
