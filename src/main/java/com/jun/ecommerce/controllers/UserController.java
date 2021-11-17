package com.jun.ecommerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jun.ecommerce.domain.User;
import com.jun.ecommerce.services.UserService;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@RestController
public class UserController {
	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("users")
	public ResponseEntity<List<User>> getAllUsers() {
		return new ResponseEntity(userService.getAllUsers(), HttpStatus.OK);
	}
	
	@PostMapping("user")
	public ResponseEntity<User> addUser(User user) {
		return new ResponseEntity<User>(userService.addUser(user), HttpStatus.OK);
	}
	
}
