package com.jun.ecommerce.data;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import com.jun.ecommerce.domain.ProductsByCategory;

@Repository
public interface ProductsByCategoryRepository extends CassandraRepository<ProductsByCategory, String>{
	Optional<List<ProductsByCategory>> findByCategory(String category);
	Optional<ProductsByCategory> findByProductIdAndCategory(UUID Id, String category);
	void deleteByCategoryAndProductId(String category, UUID id);
	void deleteByCategory(String category);
	ProductsByCategory save(ProductsByCategory product);
}
