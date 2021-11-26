package com.jun.ecommerce.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jun.ecommerce.data.ProductByCartRepository;
import com.jun.ecommerce.domain.ProductsByCart;

@ExtendWith(MockitoExtension.class)
public class ProductsByCartServiceTest {
	
	@Mock
	private ProductByCartRepository repo;
	
	@InjectMocks
	private ProductsByCartService service;
	private UUID cartId;
	private UUID productId;
	private ProductsByCart cart;
	private ProductsByCart cart2;
	private ProductsByCart cart3;
	
	@BeforeEach
	public void setup() {
		cartId = UUID.fromString("1f9a59a1-ce33-4a42-b8e8-7d3e6f8aa24e");
		productId = UUID.fromString("9984febf-9eb8-4797-9800-d4143aabe25f");
		cart = new ProductsByCart(cartId, productId,"decent chair", "chair", "junini", 50, "It's a decent chair", "https://res.cloudinary.com/rjliving-/image/fetch/f_auto,q_auto/https://www.rjliving.com.au/assets/full/SW-13-11.jpg?20210309035246", 1, 50);
		cart2 = new ProductsByCart(cartId, UUID.randomUUID(), "decent chair", "chair", "junini", 50, "It's a decent chair", "https://res.cloudinary.com/rjliving-/image/fetch/f_auto,q_auto/https://www.rjliving.com.au/assets/full/SW-13-11.jpg?20210309035246", 1, 50);
		cart3 = new ProductsByCart(cartId, UUID.randomUUID(), "decent chair", "chair", "junini", 50, "It's a decent chair", "https://res.cloudinary.com/rjliving-/image/fetch/f_auto,q_auto/https://www.rjliving.com.au/assets/full/SW-13-11.jpg?20210309035246", 1, 50);
	}
	
	@Test
	public void givenTheCartAndAProduct_shouldReturnSubtotal() {
		when(repo.findByCartIdAndProductId(cartId, productId)).thenReturn(Optional.ofNullable(cart));
		
		double subtotal = service.getSubtotalFor(cartId, productId);
		
		assertThat(subtotal, is(50));
		verify(repo).findByCartIdAndProductId(cartId, productId);
	}
}
