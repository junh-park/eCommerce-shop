package com.jun.ecommerce.services;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jun.ecommerce.data.CartsByUserRepository;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

	@Mock
	private CartsByUserRepository cartRepo;
	
	@InjectMocks
	private CartsByUserService service;
}
