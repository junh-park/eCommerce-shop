package com.jun.ecommerce.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jun.ecommerce.data.UserRepository;
import com.jun.ecommerce.domain.User;
import com.jun.ecommerce.exceptions.ResourceAlreadyExistsException;
import com.jun.ecommerce.exceptions.ResourceNotFoundException;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService  {
	private final UserRepository userRepo;
	private Map<Long, User> cache = new HashMap<>();

//	@PostConstruct
//	public void initCache() {
//		List<User> users = userRepo.findAll();
//		for (User user : users) {
//			cache.put(user.getId(), user);
//		}
//	}
	
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	//need to come back to this, to double check if the user already exists 
	public User addUser(User user) {
		if (user.getId() == null) {
			log.info("New user " + user.getFirstName() + " " + user.getLastName() + " added");
			return userRepo.save(user);
		} else {
			throw new ResourceAlreadyExistsException("This user already exists");
		}
	}

	public User getUserById(long id) {
		return userRepo.findById(id).orElseThrow(() -> 
			new ResourceNotFoundException("The User could not be found"));
	}

	public User deleteUserById(long id) {
		User user = null;
		Optional<User> optional = userRepo.findById(id);
		if (optional.isPresent()) {
			user = optional.get();
			userRepo.deleteById(optional.get().getId());
		} else {
			throw new ResourceNotFoundException("The user could not be found");
		}
		return user;
	}

	public User updateUser(User updatedUser, Long id) {
		return userRepo.findById(id).map(user -> {
				user.setFirstName(updatedUser.getFirstName());
				user.setLastName(updatedUser.getLastName());
				return userRepo.save(user);
			})
			.orElseGet(() -> {
				updatedUser.setId(id);
				return userRepo.save(updatedUser);
			});
	}
}
