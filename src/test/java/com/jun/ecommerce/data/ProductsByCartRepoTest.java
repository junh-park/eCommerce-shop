package com.jun.ecommerce.data;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;

import com.jun.ecommerce.domain.ProductsByCart;
import com.jun.ecommerce.domain.ProductsById;

@DataCassandraTest
public class ProductsByCartRepoTest {

	@Autowired
	private ProductByCartRepository repo;
	private ProductsByCart cart;
	private ProductsByCart cart2;
	private ProductsByCart cart3;
	private UUID cartId;
	private UUID productId;

	@BeforeEach
	public void setup() {
		cartId = UUID.fromString("1f9a59a1-ce33-4a42-b8e8-7d3e6f8aa24e");
		productId = UUID.fromString("9984febf-9eb8-4797-9800-d4143aabe25f");
		cart = new ProductsByCart(cartId, productId,"decent chair", "chair", "junini", 50, "It's a decent chair", "https://res.cloudinary.com/rjliving-/image/fetch/f_auto,q_auto/https://www.rjliving.com.au/assets/full/SW-13-11.jpg?20210309035246", 1, 50);
		
		cart2 = new ProductsByCart(cartId, UUID.randomUUID(), "decent chair", "chair", "junini", 50, "It's a decent chair", "https://res.cloudinary.com/rjliving-/image/fetch/f_auto,q_auto/https://www.rjliving.com.au/assets/full/SW-13-11.jpg?20210309035246", 1, 50);
		
		cart3 = new ProductsByCart(cartId, UUID.randomUUID(), "decent chair", "chair", "junini", 50, "It's a decent chair", "https://res.cloudinary.com/rjliving-/image/fetch/f_auto,q_auto/https://www.rjliving.com.au/assets/full/SW-13-11.jpg?20210309035246", 1, 50);
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
			
		repo.deleteByCartIdAndProductId(cartId, productId);
		
		Optional<ProductsByCart> returned = repo.findByCartIdAndProductId(cartId, productId);
		
		assertThat(returned.isEmpty(), is(true));
	}
}