package com.jun.ecommerce.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.jun.ecommerce.domain.ProductsByCategory;

public interface ProductByCategoryRepository extends CassandraRepository<ProductsByCategory, String>{
	Optional<List<ProductsByCategory>> findByCategory(String category);
	
	Slice<ProductsByCategory> findByCategory(String category, Pageable pageable);

	void deleteByCategory(String category);
}
