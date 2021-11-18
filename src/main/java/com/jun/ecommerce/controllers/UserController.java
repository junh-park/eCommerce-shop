package com.jun.ecommerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jun.ecommerce.domain.User;
import com.jun.ecommerce.exceptions.ResourceAlreadyExistsException;
import com.jun.ecommerce.exceptions.ResourceNotFoundException;
import com.jun.ecommerce.services.UserService;

import lombok.NoArgsConstructor;


@NoArgsConstructor
@RestController
public class UserController {
	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> allUsers = userService.getAllUsers();
		return new ResponseEntity<List<User>>(allUsers, HttpStatus.OK);
	}

	@PostMapping("/user")
	public ResponseEntity<User> addUser(@RequestBody User user) {
		User addedUser = userService.addUser(user);
		return new ResponseEntity<User>(addedUser, HttpStatus.CREATED);
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUserById(@PathVariable long id) {
		return new ResponseEntity<User>(userService.getUserById(id), HttpStatus.OK);
	}

	@DeleteMapping("/user/{id}")
	public ResponseEntity<User> deleteUserById(@PathVariable long id) {
		return new ResponseEntity<User>(userService.deleteUserById(id), HttpStatus.OK);
	}
	
	@PutMapping("/user/{id}")
	public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable Long id) {
		return new ResponseEntity<>(userService.updateUser(user, id),HttpStatus.ACCEPTED);
	}
}
