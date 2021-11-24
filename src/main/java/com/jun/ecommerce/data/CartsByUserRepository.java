package com.jun.ecommerce.data;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.jun.ecommerce.domain.CartsByUser;

public interface CartsByUserRepository extends CassandraRepository<CartsByUser, String>{
	void deleteByUserIdAndCartId(String userId, UUID cartId);
	void deleteByUserIdIn(List<String> ids);
	Optional<CartsByUser> findByUserIdAndCartId(String userId, UUID cartId);
}

