package com.jun.ecommerce.data;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.cassandra.AutoConfigureDataCassandra;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.jun.ecommerce.domain.Product;

@DataCassandraTest
public class ProductRepoTest {
	@Autowired
	private ProductRepository repo;
	
	private Product product;

	@BeforeEach
	private void setUp() {
		product = new Product();
		product.setName("Chair");
		product.setCategory("Chair");
		product.setDesc("It's a decent chair");
		product.setManufacturer("junini");
		product.setPrice(55);
		product.setImageUrl("https://res.cloudinary.com/rjliving-/image/fetch/f_auto,q_auto/https://www.rjliving.com.au/assets/full/SW-13-11.jpg?20210309035246");
	}
	
	@Test
	public void whenAddingNewProduct_shouldReturnNewProduct() {
		UUID randomUUID = UUID.randomUUID();
		product.setId(randomUUID);
		
		Product saved = repo.save(product);
		System.out.println(randomUUID);
		
		assertThat(saved, is(product));
	}
	
	@Test
	public void whenRemovingProduct_shouldRemoveProduct() {
		UUID randomUUID = UUID.randomUUID();
		product.setId(randomUUID);
		
		Product saved = repo.save(product);
		
		repo.delete(product);
		System.out.println(randomUUID);
		
		Optional<Product> optProduct = repo.findById(randomUUID);
		assertThat(optProduct.isEmpty(), is(true));
	}

	
	
}
