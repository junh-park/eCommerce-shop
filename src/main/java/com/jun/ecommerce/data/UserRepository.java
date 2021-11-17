package com.jun.ecommerce.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jun.ecommerce.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{
}
