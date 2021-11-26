package com.jun.ecommerce.data;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.jun.ecommerce.domain.ProductsById;

@Repository
public interface ProductByIdRepository extends CassandraRepository<ProductsById, UUID>{
	List<ProductsById> findByCategory(String category);
}
