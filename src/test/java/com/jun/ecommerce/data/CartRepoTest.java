package com.jun.ecommerce.data;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;

import com.jun.ecommerce.domain.Cart;

@DataCassandraTest
public class CartRepoTest {
	@Autowired
	private CartRepository repo;
	private Cart cart1;

	@BeforeEach
	public void setup() {
		cart1 = new Cart("junid", "junpark@hotmail.com");
	}

	@Test
	public void whenDeleteAll_shouldDeleteAll() {
		repo.deleteByUserIdIn(List.of(cart1.getUserId()));

		assertThat(repo.findAll(), hasSize(0));
	}

	@Test
	public void whenANewCartIsAdded_shouldAddAndReturnTheNewCart() {
		repo.deleteByUserIdIn(List.of(cart1.getUserId()));

		Cart newCart = repo.save(cart1);

		assertThat(newCart, is(cart1));
	}

	@Test
	public void whenDeleteFromTheCartByUserIdAndCartId_shouldDelteTheProduct() {
		repo.deleteByUserIdIn(List.of(cart1.getUserId()));

		repo.save(cart1);

		repo.deleteByUserIdAndCartId(cart1.getUserId(), cart1.getCartId());

		Optional<Cart> optCart = repo.findByUserIdAndCartId(cart1.getUserId(), cart1.getCartId());
		assertThat(optCart.isEmpty(), is(true));
	}
}
