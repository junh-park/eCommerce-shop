package com.jun.ecommerce.data;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;
import org.springframework.test.annotation.Rollback;

import com.jun.ecommerce.domain.ProductsByCart;
import com.jun.ecommerce.domain.ProductsById;

@DataCassandraTest
public class ProductsByCartRepoTest {

	@Autowired
	private ProductsByCartRepository repo;
	private ProductsByCart cart;
	private ProductsByCart cart2;
	private ProductsByCart cart3;
	private UUID cartId;
	private UUID productId;
	private UUID cartId2;
	private ProductsByCart cart4;
	private ProductsByCart cart5;

	@BeforeEach
	public void setup() {
		cartId = UUID.fromString("1f9a59a1-ce33-4a42-b8e8-7d3e6f8aa24e");
		cartId2 = UUID.fromString("19b13797-12e8-4e59-b447-2dad2ff7bff1");
		productId = UUID.fromString("9984febf-9eb8-4797-9800-d4143aabe25f");
	
		cart = new ProductsByCart(cartId, productId,"decent chair", "chair", "junini", 50, "It's a decent chair", "https://res.cloudinary.com/rjliving-/image/fetch/f_auto,q_auto/https://www.rjliving.com.au/assets/full/SW-13-11.jpg?20210309035246", 1, 50);
		cart2 = new ProductsByCart(cartId, UUID.randomUUID(), "decent chair", "chair", "junini", 50, "It's a decent chair", "https://res.cloudinary.com/rjliving-/image/fetch/f_auto,q_auto/https://www.rjliving.com.au/assets/full/SW-13-11.jpg?20210309035246", 1, 50);
		cart3 = new ProductsByCart(cartId, UUID.randomUUID(), "decent chair", "chair", "junini", 10, "It's a decent chair", "https://res.cloudinary.com/rjliving-/image/fetch/f_auto,q_auto/https://www.rjliving.com.au/assets/full/SW-13-11.jpg?20210309035246", 1, 50);
		cart4 = new ProductsByCart(cartId2, UUID.randomUUID(), "decent bed", "bed", "junini", 20, "It's a decent chair", "https://res.cloudinary.com/rjliving-/image/fetch/f_auto,q_auto/https://www.rjliving.com.au/assets/full/SW-13-11.jpg?20210309035246", 1, 20);
		cart5 = new ProductsByCart(cartId2, UUID.randomUUID(), "decent accessories", "accessories", "junini", 60, "It's a decent chair", "https://res.cloudinary.com/rjliving-/image/fetch/f_auto,q_auto/https://www.rjliving.com.au/assets/full/SW-13-11.jpg?20210309035246", 1, 60);
	}
	
	@Test
	public void addingNewProductToCart() {
		repo.deleteByCartId(cartId);
		
		ProductsByCart saved = repo.save(cart);
		
		assertThat(saved, is(cart));
	}
	
	@Test
	public void updatingExistingProductInCart() {
		repo.deleteByCartId(cartId);
		repo.save(cart);
		
		cart.setQuantity(2);
		
		ProductsByCart saved = repo.save(cart);
		
		assertThat(saved.getQuantity(), is(2));
	}

	@Test
	public void findingExistingProductInCartByCartId() {
		repo.deleteByCartId(cartId);
		repo.save(cart);
		repo.save(cart2);
		repo.save(cart3);
		repo.save(cart4);
		repo.save(cart5);
		
		Optional<List<ProductsByCart>> saved = repo.findByCartId(cartId);
		
		assertThat(saved.get(), hasSize(3));
		assertThat(saved.get(), containsInAnyOrder(cart, cart2, cart3));
	}
	
	@Test
	public void findingExistingProductInCartByCartIdAndProductId() {
		Optional<ProductsByCart> saved = repo.findByCartIdAndProductId(cartId, productId);
		
		assertThat(saved.get(), is(cart));
	}
	
	@Test
	public void deletingExistingProductInCartByCartIdAndProductId() {
		repo.deleteByCartId(cartId);
		repo.save(cart);
		repo.save(cart2);
		repo.save(cart3);
		repo.save(cart4);
		repo.save(cart5);
			
		repo.deleteByCartIdAndProductId(cartId, productId);
		
		Optional<ProductsByCart> returned = repo.findByCartIdAndProductId(cartId, productId);
		
		assertThat(returned.isEmpty(), is(true));
	}
}
