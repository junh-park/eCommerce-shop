package com.jun.ecommerce.services;

import java.util.List;

import com.jun.ecommerce.domain.User;

public interface UserService {

	List<User> getAllUsers();

	User addUser(User user);

	User getUserById(long id);

	User deleteUserById(long id);

	User updateUser(User updatedUser, Long id);

}