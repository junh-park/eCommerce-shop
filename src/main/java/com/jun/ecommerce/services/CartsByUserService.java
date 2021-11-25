package com.jun.ecommerce.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.jun.ecommerce.data.CartsByUserRepository;
import com.jun.ecommerce.domain.CartsByUser;
import com.jun.ecommerce.exceptions.ResourceAlreadyExistsException;
import com.jun.ecommerce.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CartsByUserService {

	private final CartsByUserRepository repo;

	public List<CartsByUser> getAllUserCarts(String userId) {
		return getAllCarts(userId);
	}

	public CartsByUser updateUserCart(CartsByUser updatedCart) {
		Optional<CartsByUser> optCart = repo.findByUserIdAndCartId(updatedCart.getUserId(), updatedCart.getCartId());
		
		if (optCart.isPresent()) {
			CartsByUser cart= optCart.get();
			cart.setActive(updatedCart.isActive());
			cart.setAddress(updatedCart.getAddress());
			return repo.save(cart);
		} else {
			throw new ResourceNotFoundException();
		}
	}

	public CartsByUser getActiveUserCart(String userId) {
		List<CartsByUser> allCarts = getAllCarts(userId);

		return allCarts.stream().filter(cart -> cart.isActive()).collect(uniqueCartChecker());
	}

	public CartsByUser addNewCart(CartsByUser newCart) {
		Optional<CartsByUser> optCart = repo.findByUserIdAndCartId(newCart.getUserId(), newCart.getCartId());

		if (optCart.isEmpty()) {
			return repo.save(newCart);
		} else {
			throw new ResourceAlreadyExistsException("Resource already exists");
		}
	}

	public void deleteCart(CartsByUser cart) {
		Optional<CartsByUser> optCart = repo.findByUserIdAndCartId(cart.getUserId(), cart.getCartId());

		optCart.ifPresent(c -> repo.deleteByUserIdAndCartId(c.getUserId(), c.getCartId()));
	}

	public CartsByUser findbyUserIdAndCartId(String userId, UUID cartId) {
		return null;
	}

	private Collector<CartsByUser, ?, CartsByUser> uniqueCartChecker() {
		return Collectors.collectingAndThen(Collectors.toList(), list -> {
			if (list.size() != 1)
				throw new IllegalStateException("Multiple carts found");
			return list.get(0);
		});
	}

	private List<CartsByUser> getAllCarts(String userId) {
		Optional<List<CartsByUser>> optCarts = repo.findAllByUserId(userId);

		if (optCarts.isPresent()) {
			return optCarts.get();
		} else {
			throw new ResourceNotFoundException("No carts found");
		}
	}

}
