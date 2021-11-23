package com.jun.ecommerce.data;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.jun.ecommerce.domain.ProductsByCategory;

@DataCassandraTest
public class ProductByCategoryRepoTest {
	@Autowired
	private ProductsByCategoryRepository repo;
	
	private ProductsByCategory product;

	@BeforeEach
	private void setUp() {
		product = new ProductsByCategory();
		product.setName("Chair");
		product.setCategory("Chair");
		product.setDesc("It's a decent chair");
		product.setManufacturer("junini");
		product.setPrice(55);
		product.setImageUrl("https://res.cloudinary.com/rjliving-/image/fetch/f_auto,q_auto/https://www.rjliving.com.au/assets/full/SW-13-11.jpg?20210309035246");
	}
	
	@Test
	public void whenAddingNewProduct_shouldReturnNewProduct() {
		repo.deleteByCategory("Chair");

		ProductsByCategory saved = repo.save(product);
		
		assertThat(saved, is(product));
	}
	
	@Test
	public void whenFindingProductsByCategory_shouldReturnProductsByCategory() {
		repo.deleteByCategory("Chair");
		
		ProductsByCategory saved1 = repo.save(product);
		
		Optional<List<ProductsByCategory>> optProduct = repo.findByCategory("Chair");
		
		assertThat(optProduct.get(), hasSize(1));
		assertThat(optProduct.get().get(0), is(product));
	}
	
	@Test
	public void whenDuplicatedProductAdded_shouldReturn1() {
		repo.deleteByCategory("Chair");
		
		ProductsByCategory saved1 = repo.save(product);
		ProductsByCategory saved2 = repo.save(product);

		Optional<List<ProductsByCategory>> optProduct = repo.findByCategory("Chair");
		
		assertThat(optProduct.get(), hasSize(1));
		assertThat(optProduct.get().get(0), is(product));
	}
	
}
