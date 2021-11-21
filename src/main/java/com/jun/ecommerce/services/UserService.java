package com.jun.ecommerce.services;

import java.util.List;
import java.util.UUID;

import com.jun.ecommerce.domain.User;

public interface UserService {

	List<User> getAllUsers();

	User addUser(User user);

	User getUserById(UUID id);

	User deleteUserById(UUID id);

	User updateUser(User updatedUser, UUID id);

}