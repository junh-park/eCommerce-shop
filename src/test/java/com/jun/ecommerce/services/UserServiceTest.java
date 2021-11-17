package com.jun.ecommerce.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.dialect.function.AvgWithArgumentCastFunction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.jun.ecommerce.data.UserRepository;
import com.jun.ecommerce.domain.User;

@WebMvcTest
public class UserServiceTest {
	@InjectMocks
	UserService userService;
	@Mock
	UserRepository userRepo;

	private List<User> users;
	private User user1;
	private User user2;
	
	@BeforeEach
	public void setUp() {
		user1 = new User("jun", "park");
		user2 = new User("dodo", "xu");
		users = new ArrayList<>();
		users.add(user1);
		users.add(user2);
	}
	
	@Test
	public void whenGetAllUser_ShouldReturnListOfUsers() {
		when(userRepo.findAll()).thenReturn(users);
		
		List<User> allUsers = userService.getAllUsers();
		
		compareAllUsers(allUsers, users);
		verify(userRepo).findAll();
		verify(userRepo, times(1)).findAll();
	}
	
	@Test
	public void whenANewUser_ShouldSaveTheUser() {
		when(userRepo.save(user1)).thenReturn(user1);
		
		User savedUser = userService.addUser(user1);
		
		compareUser(savedUser, user1);
		verify(userRepo).save(user1);
		verify(userRepo, times(1)).save(user1);
	}
	
	@Test
	public void whenRequestedUsingId_ShouldReturnTheUser() {
		when(userRepo.findById(2L)).thenReturn(Optional.ofNullable(user2));
		
		User returnedUser = userService.getUserById(2L);
		
		compareUser(returnedUser, user2);
	}
	
//	@Test
//	public void whenDeleteUserUsingID_ShouldDeleteUserOfThatId() {
//		when(userRepo.findById(user1.getId())).thenReturn(Optional.of(user1));
//		
//		userService.deleteUserById(1L);
//		
//		verify(userRepo,times(1)).findById(1L);
//		verify(userRepo,times(1)).deleteById(1L);
//	}
	
	private void compareAllUsers(List<User> users1, List<User> users2) {
		assertThat(users1.size(), is(users2.size()));
		assertThat(users1, contains(users.toArray()));
	}
	
	private void compareUser(User user1, User user2) {
		assertThat(user1.getFirstName(), is(user2.getFirstName()));
		assertThat(user1.getLastName(), is(user2.getLastName()));
	}
}
