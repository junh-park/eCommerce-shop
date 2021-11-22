package com.jun.ecommerce.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jun.ecommerce.data.ProductByCategoryRepository;
import com.jun.ecommerce.data.ProductRepository;
import com.jun.ecommerce.domain.ProductsById;
import com.jun.ecommerce.domain.ProductsByCategory;
import com.jun.ecommerce.exceptions.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
	
	@Mock
	private ProductRepository productRepo;
	
	@Mock
	private ProductByCategoryRepository byCategoryRepo;
	
	@InjectMocks
	private ProductServiceImpl service;

	private ProductsByCategory product1;
	private ProductsByCategory product2;
	private ProductsByCategory product3;
	
	@BeforeEach
	public void setup() {
		product1 = new ProductsByCategory("decent chair", "chair", "junini", 50, "It's a decent chair", "https://res.cloudinary.com/rjliving-/image/fetch/f_auto,q_auto/https://www.rjliving.com.au/assets/full/SW-13-11.jpg?20210309035246");
		product2 = new ProductsByCategory("pretty good chair", "chair", "junini", 30, "It's a pretty decent chair", "https://res.cloudinary.com/rjliving-/image/fetch/f_auto,q_auto/https://www.rjliving.com.au/assets/full/SW-13-11.jpg?20210309035246");
		product3 = new ProductsByCategory("a comfortable bed", "bed", "junini", 30, "It's a decent bed", "https://www.beddingwarehouse.com.au/wp-content/uploads/2020/08/Milan-Bed-Frame.jpg");

	}
	
	@Test
	public void whenFindByCategory_shouldReturnProductsInTheCategory() {
		List<ProductsByCategory> products = List.of(product1, product2);
		
		when(byCategoryRepo.findByCategory("chair")).thenReturn(Optional.ofNullable(products));
		
		List<ProductsByCategory> allProductsByChair = service.getAllProductsByCategory("chair");
		
		assertThat(allProductsByChair, containsInAnyOrder(products));
		verify(byCategoryRepo, times(1)).findByCategory("chair");
	}
	
	@Test
	public void whenUpdateRequest_shouldUpdateAndReturnUpdatedProduct() {	
		product1.setPrice(100);
		
		when(byCategoryRepo.save(product1)).thenReturn(product1);
		
		List<ProductsById> allProductsByChair = service.updateProduct(product1);
		
		assertThat(allProductsByChair, containsInAnyOrder(products));
		verify(byCategoryRepo, times(1)).findByCategory("chair");
	}
	
}
