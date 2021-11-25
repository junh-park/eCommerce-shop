package com.jun.ecommerce.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jun.ecommerce.data.CartsByUserRepository;
import com.jun.ecommerce.domain.CartsByUser;
import com.jun.ecommerce.exceptions.ResourceAlreadyExistsException;

@ExtendWith(MockitoExtension.class)
public class CartsByUserServiceTest {

	@Mock
	private CartsByUserRepository cartRepo;
	
	@InjectMocks
	private CartsByUserService service;
	private CartsByUser cart1;
	private CartsByUser cart2;
	private CartsByUser cart3;
	private CartsByUser cart4;
	private List<CartsByUser> carts;


	@BeforeEach
	public void setup() {
		cart1 = new CartsByUser("junid", UUID.randomUUID(), false, "melbourne");
		cart2 = new CartsByUser("junid", UUID.randomUUID(), true, "sydney");
		cart3 = new CartsByUser("junid", UUID.randomUUID(), false, "melbourne");
		cart4 = new CartsByUser("dodoid", UUID.randomUUID(), true, "sydney");

		carts = List.of(cart1, cart2);
	}
	
	@Test
	public void returnAllCartGivenUser() {
		when(cartRepo.findAllByUserId("junid")).thenReturn(Optional.ofNullable(carts));
		
		List<CartsByUser> userCarts = service.getAllUserCarts("junid");

		assertThat(userCarts, hasSize(2));
		verify(cartRepo).findAllByUserId("junid");
	}
	
	@Test
	public void returnTheActiveCartGivenUser() {
		when(cartRepo.findAllByUserId("junid")).thenReturn(Optional.ofNullable(carts));
		
		CartsByUser userCart = service.getActiveUserCart("junid");
 
		assertThat(userCart, is(cart2));
		verify(cartRepo).findAllByUserId("junid");
	}
	
	@Test
	public void deleteByUserIdAndCartID() {
		when(cartRepo.findByUserIdAndCartId(cart1.getUserId(), cart1.getCartId())).thenReturn(Optional.ofNullable(cart1));
		doNothing().when(cartRepo).deleteByUserIdAndCartId(cart1.getUserId(), cart1.getCartId());

		service.deleteCart(cart1);
		
		verify(cartRepo, times(1)).findByUserIdAndCartId(cart1.getUserId(), cart1.getCartId());
		verify(cartRepo, times(1)).deleteByUserIdAndCartId(cart1.getUserId(), cart1.getCartId());
	}
	
	@Test
	public void updateUser() {
		cart2.setUserId(cart1.getUserId());
		cart2.setCartId(cart1.getCartId());
		when(cartRepo.findByUserIdAndCartId(cart1.getUserId(), cart1.getCartId())).thenReturn(Optional.ofNullable(cart1));
		when(cartRepo.save(cart2)).thenReturn(cart2);

		CartsByUser updatedCart = service.updateUserCart(cart2);
		
		assertThat(updatedCart, is(cart2));
		verify(cartRepo, times(1)).findByUserIdAndCartId(cart1.getUserId(), cart1.getCartId());
		verify(cartRepo, times(1)).save(cart2);
	}
	
	@Test
	public void whenMultipleActiveCartsFound_throwExceptionGivenUser() {
		cart1.setActive(true);
		when(cartRepo.findAllByUserId("junid")).thenReturn(Optional.ofNullable(carts));

		IllegalStateException exception = 
				Assertions.assertThrows(IllegalStateException.class, () -> service.getActiveUserCart("junid"),
						"Multiple carts found");
		
		assertThat(exception.getMessage(), is("Multiple carts found"));
		verify(cartRepo).findAllByUserId("junid");
	}
	
	@Test
	public void shouldReturnTheSavedCart_whenANewCartAdded() {
		when(cartRepo.save(cart4)).thenReturn(cart4);
		
		CartsByUser userCart = service.addNewCart(cart4);
 
		assertThat(userCart, is(cart4));
		verify(cartRepo).save(cart4);
	}
	
	@Test
	public void shouldThrowException_whenANewCartAlreadyExists() {
		when(cartRepo.findByUserIdAndCartId(any(), any())).thenReturn(Optional.ofNullable(cart4));
		
		ResourceAlreadyExistsException exception = Assertions.assertThrows(ResourceAlreadyExistsException.class, 
				() -> service.addNewCart(cart4), "Resource already exists");
		
		assertThat(exception.getMessage(), is("Resource already exists"));
		verify(cartRepo).findByUserIdAndCartId(any(), any());
	}
}
