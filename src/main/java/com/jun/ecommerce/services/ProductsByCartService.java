package com.jun.ecommerce.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jun.ecommerce.data.ProductsByCategoryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductsByCartService {
	private final ProductsByCategoryRepository repo;

	public double getSubtotalFor(UUID cartId, UUID productId) {
		return 0;
	}

}
