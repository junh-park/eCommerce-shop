//package com.jun.ecommerce.controller;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.springframework.http.MediaType.APPLICATION_JSON;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.jun.ecommerce.controllers.UserController;
//import com.jun.ecommerce.domain.User;
//import com.jun.ecommerce.services.UserServiceImpl;
//
//@ExtendWith(MockitoExtension.class)
//public class UserControllerTest {
//	@Autowired
//	MockMvc mockMvc;
//
//	@Mock
//	UserServiceImpl userService;
//
//	@InjectMocks
//	private UserController controller;
//
//	private List<User> users;
//	private User user1;
//	private User user2;
//
//	@BeforeEach
//	public void setUp() {
//		user1 = new User("jun", "park", "junpark", "password", "junpark0118@hotmail.com");
//		user2 = new User("dodo", "xu", "dodoxu", "password", "dodoxu@hotmail.com");
//		user1.setId(1L);
//		user2.setId(2L);
//
//		users = new ArrayList<>();
//		users.add(user1);
//		users.add(user2);
//
//		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
//	}
//
//	@Test
//	public void whenGetUsersRequested_returnUsers() throws Exception {
//		when(userService.getAllUsers()).thenReturn(users);
//
//		mockMvc.perform(get("/users").contentType(APPLICATION_JSON).content(mapJsonToString(user1))).andDo(print())
//				.andExpect(status().isOk()).andExpect(jsonPath("$[1].firstName").value(users.get(1).getFirstName()));
//		verify(userService, times(1)).getAllUsers();
//	}
//
//	@Test
//	public void whenGetSpecificUserRequestById_shouldReturnTheSpecificUserById() throws Exception {
//		when(userService.getUserById(user1.getId())).thenReturn(user1);
//
//		mockMvc.perform(get("/user/{id}", 1L).contentType(APPLICATION_JSON).content(mapJsonToString(user1)))
//				.andDo(print()).andExpect(status().isOk())
//				.andExpect(jsonPath("$.firstName").value(user1.getFirstName()));
//		verify(userService, times(1)).getUserById(user1.getId());
//	}
//
//	@Test
//	@Disabled
//	public void whenANewUserIsAdded_shouldReturnAddedUser() throws Exception {
//		User user3 = new User("junnieeee", "park", "junnieeepark", "password", "junpark0118@hotmail.com");
//		when(userService.addUser(any(User.class))).thenReturn(user3);
//
//		mockMvc.perform(post("/user").contentType(APPLICATION_JSON).content(mapJsonToString(user3))).andDo(print())
//				.andExpect(status().isCreated()).andExpect(jsonPath("$.firstName").value(user3.getFirstName()))
//				.andExpect(jsonPath("$.lastName").value(user3.getLastName()));
//		verify(userService, times(1)).addUser(user3);
//	}
//	
//	@Test
//	public void whenUpdateRequestWithId_shouldUpdateTheUserOfThatId() throws Exception {
//		User user = new User("junnieeee", "park", "junnieeepark", "password", "junpark0118@hotmail.com");
//		when(userService.updateUser(user, user.getId())).thenReturn(user);
//
//		mockMvc.perform(put("/user/{id}", 1L).contentType(APPLICATION_JSON).content(mapJsonToString(user)))
//				.andDo(print())
//				.andExpect(status().isAccepted())
//				.andExpect(jsonPath("$.firstName").value(user.getFirstName()))
//				.andExpect(jsonPath("$.lastName").value(user.getLastName()));
//	}
//
//	@Test
//	public void whenDeleteUserById_shouldDeleteUserById() throws Exception {
//		when(userService.deleteUserById(user1.getId())).thenReturn(user1);
//
//		mockMvc.perform(delete("/user/{id}", 1L).contentType(APPLICATION_JSON).content(mapJsonToString(user1)))
//				.andDo(print()).andExpect(status().isOk());
//		verify(userService, times(1)).deleteUserById(user1.getId());
//	}
//
//
//	public static String mapJsonToString(final Object user) {
//		try {
//			return new ObjectMapper().writeValueAsString(user);
//		} catch (JsonProcessingException e) {
//			throw new RuntimeException(e);
//		}
//	}
//
//}
