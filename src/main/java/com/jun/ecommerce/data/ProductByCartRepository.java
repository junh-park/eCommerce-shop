package com.jun.ecommerce.data;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.jun.ecommerce.domain.ProductsByCart;
import com.jun.ecommerce.domain.ProductsById;

public interface ProductByCartRepository extends CassandraRepository<ProductsByCart, UUID>{
	void deleteByCartId(UUID cartId);
	void deleteByCartIdAndProductId(UUID cartId, UUID productId);
	Optional<ProductsByCart> findByCartIdAndProductId(UUID cartId, UUID productId);
	
}
