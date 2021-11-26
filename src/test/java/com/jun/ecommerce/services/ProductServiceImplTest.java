package com.jun.ecommerce.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
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

import com.jun.ecommerce.data.ProductsByCategoryRepository;
import com.jun.ecommerce.data.ProductByIdRepository;
import com.jun.ecommerce.domain.ProductsByCategory;
import com.jun.ecommerce.domain.ProductsById;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
	
	@Mock
	private ProductByIdRepository productRepo;
	
	@Mock
	private ProductsByCategoryRepository byCategoryRepo;

	@InjectMocks
	private ProductServiceImpl service;

	private ProductsByCategory product1;
	private ProductsByCategory product2;
	private ProductsById product3;
	private ProductsById product4;

	private List<ProductsByCategory> products;
	
	@BeforeEach
	public void setup() {
		product1 = new ProductsByCategory("decent chair", "chair", "junini", 50, "It's a decent chair", "https://res.cloudinary.com/rjliving-/image/fetch/f_auto,q_auto/https://www.rjliving.com.au/assets/full/SW-13-11.jpg?20210309035246");
		product2 = new ProductsByCategory("pretty good chair", "chair", "junini", 30, "It's a pretty decent chair", "https://res.cloudinary.com/rjliving-/image/fetch/f_auto,q_auto/https://www.rjliving.com.au/assets/full/SW-13-11.jpg?20210309035246");
		product3 = new ProductsById("a comfortable bed", "bed", "junini", 30, "It's a decent bed", "https://www.beddingwarehouse.com.au/wp-content/uploads/2020/08/Milan-Bed-Frame.jpg");
		product4 = new ProductsById("a really really good bed", "bed", "junini", 100, "It's a really really good bed", "https://www.beddingwarehouse.com.au/wp-content/uploads/2020/08/Milan-Bed-Frame.jpg");

		products = List.of(product1, product2);
	}
	
	@Test
	public void whenFindByCategory_shouldReturnProductsInTheCategory() {
		List<ProductsByCategory> products = List.of(product1, product2);
		
		when(byCategoryRepo.findByCategory("chair")).thenReturn(Optional.ofNullable(products));
		
		List<ProductsByCategory> allProductsByChair = service.getProductsByCategory("chair");
		
		assertThat(allProductsByChair, hasSize(2));
		assertThat(allProductsByChair, containsInAnyOrder(product1,product2));
		verify(byCategoryRepo, times(1)).findByCategory("chair");
	}
	
	@Test
	public void whenUpdateRequest_shouldUpdateAndReturnUpdatedProduct() {	
		product1.setPrice(100);
		
		when(byCategoryRepo.findByIdAndCategory(product1.getId(), product1.getCategory()))
			.thenReturn(Optional.ofNullable(product1));
		when(byCategoryRepo.save(product1)).thenReturn(product1);
		
		ProductsByCategory updatedProduct = service.updateProduct(product1);
		
		assertThat(updatedProduct, is(product1));
		verify(byCategoryRepo, times(1)).findByIdAndCategory(product1.getId(), product1.getCategory());
		verify(byCategoryRepo, times(1)).save(product1);
		verify(productRepo,times(1)).save(any(ProductsById.class));
	}
	
	@Test
	public void whenUpdateRequestById_shouldUpdateAndReturnUpdatedProduct_AlsoFromProductByCategoryTable() {	
		product1.setPrice(100);
		
		when(productRepo.findById(product3.getId()))
			.thenReturn(Optional.ofNullable(product3));
		when(productRepo.save(product3)).thenReturn(product3);
		
		ProductsById updatedProduct = service.updateProduct(product3);
		
		assertThat(updatedProduct, is(product3));
		verify(productRepo, times(1)).findById(product3.getId());
		verify(productRepo, times(1)).save(product3);
		verify(byCategoryRepo,times(1)).save(any(ProductsByCategory.class));
	}
	
	@Test
	public void whenGetAllByIdRequested_shouldReturnAllProductsById() {
		List<ProductsById> products = List.of(product3,product4);
		when(productRepo.findAll()).thenReturn(products);
		
		List<ProductsById> allProducts = service.getAllProducts();
		
		assertThat(allProducts, hasSize(2));
		assertThat(allProducts, contains(product3, product4));
		verify(productRepo,times(1)).findAll();
	}
	
	@Test
	public void whenGetById_shouldReturnTheProductOfThatId() {
		when(productRepo.findById(product3.getId())).thenReturn(Optional.ofNullable(product3));
		
		ProductsById returnedProduct = service.getProductById(product3.getId());
		
		assertThat(returnedProduct, is(product3));
		verify(productRepo).findById(product3.getId());
	}
	
	@Test
	public void whenAddById_shouldReturnTheProductOfThatId_AlsoFromProductByCategoryTable() {
		when(productRepo.save(product3)).thenReturn(product3);
		
		ProductsById returnedProduct = service.addProduct(product3);
		
		assertThat(returnedProduct, is(product3));
		verify(productRepo).findById(product3.getId());
	}
	
	@Test
	public void whenDeleteById_shouldDeleteTheProductOfThatId() {
		when(productRepo.findById(product3.getId())).thenReturn(Optional.ofNullable(product3));
		doNothing().when(productRepo).deleteById(product3.getId());
		doNothing().when(byCategoryRepo).delete(any(ProductsByCategory.class));

		service.deleteProduct(product3.getId());
		
		verify(productRepo, times(1)).findById(product3.getId());
		verify(byCategoryRepo,times(1)).delete(any(ProductsByCategory.class));
		verify(productRepo, times(1)).deleteById(product3.getId());
	}

}
