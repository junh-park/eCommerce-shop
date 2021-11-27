package com.jun.ecommerce.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
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
import org.springframework.test.context.event.annotation.BeforeTestClass;

import com.jun.ecommerce.data.ProductsByCartRepository;
import com.jun.ecommerce.domain.ProductsByCart;

@ExtendWith(MockitoExtension.class)
public class ProductsByCartServiceTest {
	
	@Mock
	private ProductsByCartRepository repo;
	
	@InjectMocks
	private ProductsByCartService service;
	private UUID cartId;
	private UUID productId;
	private ProductsByCart cart;
	private ProductsByCart cart2;
	private ProductsByCart cart3;
	
	@BeforeEach
	public void setup() {
		cart = new ProductsByCart(
				UUID.fromString("1f9a59a1-ce33-4a42-b8e8-7d3e6f8aa24e"), 
				UUID.fromString("9984febf-9eb8-4797-9800-d4143aabe25f"),
				"decent chair", "chair", "junini", 50, "It's a decent chair", 
				"https://res.cloudinary.com/rjliving-/image/fetch/f_auto,q_auto/https://www.rjliving.com.au/assets/full/SW-13-11.jpg?20210309035246",
				1,
				50);
//		cart2 = new ProductsByCart(cartId, UUID.randomUUID(), "decent chair", "chair", "junini", 50, "It's a decent chair", "https://res.cloudinary.com/rjliving-/image/fetch/f_auto,q_auto/https://www.rjliving.com.au/assets/full/SW-13-11.jpg?20210309035246", 1, 50);
//		cart3 = new ProductsByCart(cartId, UUID.randomUUID(), "decent chair", "chair", "junini", 50, "It's a decent chair", "https://res.cloudinary.com/rjliving->></image/fetch/f_auto,q_auto/https://www.rjliving.com.au/assets/full/SW-13-11.jpg?20210309035246", 1, 50);
	}
	
	@Test
	public void givenTheCartAndAProduct_shouldReturnSubtotal() {
		when(repo.findByCartIdAndProductId(cartId, productId)).thenReturn(Optional.ofNullable(cart));
		
		double subtotal = service.getSubtotalFor(cartId, productId);
		
		assertThat(subtotal, is(50.0));
		verify(repo).findByCartIdAndProductId(cartId, productId);
	}
	
	@Test
	public void addingNewProductAndSetSubtotal_givenCartIdAndProductId() {
		cart.setQuantity(2);
		when(repo.save(cart)).thenReturn(cart);
		
		ProductsByCart addedProduct = service.addNewProductToCart(cart);
		
		assertThat(addedProduct, is(cart));
		assertThat(addedProduct.getSubtotal(), is(100.0));
		verify(repo).save(cart);
	}
	
	@Test
	public void updateExistingQuantityAndSubtotal_givenCartIdAndProductId() {
		when(repo.findByCartIdAndProductId(cart.getCartId(), cart.getProductId())).thenReturn(Optional.ofNullable(cart));
		
		ProductsByCart product = service.getExistingProduct(cart);
		
		assertThat(product, is(cart));
		assertThat(product.getSubtotal(), is(50.0));
		
		cart.setQuantity(5);
		
		ProductsByCart addedProduct = service.updateQuantity(cart);
		
		assertThat(addedProduct, is(cart));
		assertThat(addedProduct.getSubtotal(), is(250.0));
		verify(repo).save(cart);
	}
}
