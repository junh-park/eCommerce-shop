package com.jun.ecommerce.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.jun.ecommerce.controllers.UserController;
import com.jun.ecommerce.domain.User;
import com.jun.ecommerce.services.UserService;

@WebMvcTest
public class UserControllerTest {
	@Autowired
	MockMvc mockMvc;
	
	@Mock
	UserService userService;
	
	@InjectMocks
	private UserController controller;

	private List<User> users;
	
	@BeforeEach
	public void setUp() {
		User user1 = new User("jun", "park");
		User user2 = new User("dodo", "xu");
		users = new ArrayList<>();
		users.add(user1);
		users.add(user2);
		
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@Test
	public void whenGetUsersRequested_returnUsers() throws Exception {
		when(userService.getAllUsers()).thenReturn(users);
		
		mockMvc.perform(get("/users")
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
 			.andExpect(status().isOk())
 			.andExpect(jsonPath("$[1].firstName").value(users.get(1).getFirstName()));
		verify(userService).getAllUsers();
		verify(userService, times(1)).getAllUsers();
	}
}
