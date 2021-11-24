package com.jun.ecommerce.data;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;

import com.jun.ecommerce.domain.CartsByUser;

@DataCassandraTest
public class CartsByUserRepoTest {
	@Autowired
	private CartsByUserRepository repo;
	private CartsByUser cart1;
	private CartsByUser cart2;

	@BeforeEach
	public void setup() {
		cart1 = new CartsByUser("junid", UUID.randomUUID(), true, "melbourne");
		cart2 = new CartsByUser("junid", UUID.randomUUID(), false, "melbourne");
	}

	@Test
	public void whenDeleteAll_shouldDeleteAll() {
		repo.deleteByUserIdIn(List.of(cart1.getUserId()));

		assertThat(repo.findAll(), hasSize(0));
	}

	@Test
	public void whenANewCartIsAdded_shouldAddAndReturnTheNewCart() {
		repo.deleteByUserIdIn(List.of(cart1.getUserId()));

		CartsByUser newCart = repo.save(cart1);

		assertThat(newCart, is(cart1));
	}

	@Test
	public void whenDeleteFromTheCartByUserIdAndCartId_shouldDelteTheProduct() {
		repo.deleteByUserIdIn(List.of(cart1.getUserId(), cart2.getUserId()));

		repo.save(cart1);

		repo.deleteByUserIdAndCartId(cart1.getUserId(), cart1.getCartId());

		Optional<CartsByUser> optCart = repo.findByUserIdAndCartId(cart1.getUserId(), cart1.getCartId());
		assertThat(optCart.isEmpty(), is(true));
	}
}
