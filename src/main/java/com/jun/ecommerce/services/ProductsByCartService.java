package com.jun.ecommerce.services;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.jun.ecommerce.data.ProductsByCartRepository;
import com.jun.ecommerce.domain.ProductsByCart;
import com.jun.ecommerce.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductsByCartService {
	private final ProductsByCartRepository repo;

	public double getSubtotalFor(ProductsByCart productInCart) {
		return findTemplate(productInCart, existingProductInCart -> existingProductInCart).getSubtotal();
	}
	
	public ProductsByCart addNewProductToCart(ProductsByCart productInCart) {
		productInCart.setSubtotal(productInCart.getPrice() * productInCart.getQuantity());
		return repo.save(productInCart);
	}

	public ProductsByCart getExistingProduct(ProductsByCart productInCart) {
		return findTemplate(productInCart, existingProductInCart -> existingProductInCart);
	}

	public ProductsByCart updateQuantity(ProductsByCart productInCart) {
		return findTemplate(productInCart, existingCart -> {
			existingCart.setQuantity(productInCart.getQuantity());
			existingCart.setSubtotal(productInCart.getQuantity() * productInCart.getPrice());
			return repo.save(existingCart);
		});
	}

	private ProductsByCart findTemplate(ProductsByCart productInCart, 
			Function<ProductsByCart, ProductsByCart> logic) {
		Optional<ProductsByCart> optProduct = repo.findByCartIdAndProductId(productInCart.getCartId(), 
				productInCart.getProductId());

		if (optProduct.isPresent()) {
			return logic.apply(optProduct.get());
		} else {
			throw new ResourceNotFoundException();
		}
	}
}
