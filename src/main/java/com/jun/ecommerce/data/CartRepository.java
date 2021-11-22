package com.jun.ecommerce.data;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.jun.ecommerce.domain.Cart;

public interface CartRepository extends CassandraRepository<Cart, String>{
	void deleteByUserIdAndCartId(String userId, UUID cartId);
	void deleteByUserIdIn(List<String> ids);
	Optional<Cart> findByUserIdAndCartId(String userId, UUID cartId);
}

