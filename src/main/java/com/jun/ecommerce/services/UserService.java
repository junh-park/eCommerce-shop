package com.jun.ecommerce.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jun.ecommerce.data.UserRepository;
import com.jun.ecommerce.domain.User;
import com.jun.ecommerce.exceptions.ResourceNotFoundException;

import lombok.NoArgsConstructor;

@Service
public class UserService {
	private UserRepository userRepo;

	public UserService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	public User addUser(User user) {
		return userRepo.save(user);
	}

	public User getUserById(long id) {
		return userRepo.findById(id).orElseThrow(() -> 
		new ResourceNotFoundException("The User could not be found"));
	}

	public void deleteUserById(long id) {
		Optional<User> optional = userRepo.findById(id);
		if(optional.isPresent()) {
			userRepo.deleteById(optional.get().getId());
		} else {
			throw new ResourceNotFoundException("The user could not be found");
		}
	}
}
