package com.jun.ecommerce.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
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
import org.springframework.boot.test.context.SpringBootTest;
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
	private ProductsByCart productInCart;
	private ProductsByCart productInCart2;
	private ProductsByCart productInCart3;
	
	@BeforeEach
	public void setup() {
		cartId = UUID.fromString("1f9a59a1-ce33-4a42-b8e8-7d3e6f8aa24e");
		productId = UUID.fromString("9984febf-9eb8-4797-9800-d4143aabe25f");
		productInCart = new ProductsByCart(cartId, UUID.fromString("9984febf-9eb8-4797-9800-d4143aabe25f"), "decent chair", "chair", "junini", 50, "It's a decent chair",				"https://res.cloudinary.com/rjliving-/image/fetch/f_auto,q_auto/https://www.rjliving.com.au/assets/full/SW-13-11.jpg?20210309035246", 1, 50);
		productInCart2 = new ProductsByCart(cartId, UUID.fromString("5367fabc-10cc-49b1-ad51-46356d75ab8d"), "decent chair", "chair", "junini", 50, "It's a decent chair", "https://res.cloudinary.com/rjliving-/image/fetch/f_auto,q_auto/https://www.rjliving.com.au/assets/full/SW-13-11.jpg?20210309035246", 1, 50);
		productInCart3 = new ProductsByCart(cartId, UUID.fromString("19b13797-12e8-4e59-b447-2dad2ff7bff1"), "decent chair", "chair", "junini", 50, "It's a decent chair", "https://res.cloudinary.com/rjliving->></image/fetch/f_auto,q_auto/https://www.rjliving.com.au/assets/full/SW-13-11.jpg?20210309035246", 1, 50);
	}
	
	@Test
	public void givenTheCartAndAProduct_shouldReturnSubtotal() {
		when(repo.findByCartIdAndProductId(cartId, productId)).thenReturn(Optional.ofNullable(productInCart));
		
		double subtotal = service.getSubtotalFor(productInCart);
		
		assertThat(subtotal, is(50.0));
		verify(repo).findByCartIdAndProductId(cartId, productId);
	}
	
	@Test
	public void givenCartIdAndProductId_addingNewProductAndSetSubtotal() {
		productInCart.setQuantity(2);
		when(repo.save(productInCart)).thenReturn(productInCart);
		
		ProductsByCart addedProduct = service.addNewProductToCart(productInCart);
		
		assertThat(addedProduct, is(productInCart));
		assertThat(addedProduct.getSubtotal(), is(100.0));
		verify(repo).save(productInCart);
	}
	
	@Test
	public void givenCartIdAndProductId_updateExistingQuantityAndSubtotal() {
		productInCart.setQuantity(5);
		when(repo.findByCartIdAndProductId(productInCart.getCartId(), productInCart.getProductId())).thenReturn(Optional.ofNullable(productInCart));
		when(repo.save(any(ProductsByCart.class))).thenReturn(productInCart);
		
		ProductsByCart addedProduct = service.updateQuantity(productInCart);
		
		verify(repo, times(1)).findByCartIdAndProductId(cartId, productId);
		verify(repo).save(productInCart);
		assertThat(addedProduct, is(productInCart));
		assertThat(addedProduct.getSubtotal(), is(250.0));
	}
}
