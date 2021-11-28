package com.jun.ecommerce.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jun.ecommerce.controllers.ProductController;
import com.jun.ecommerce.domain.ProductsById;
import com.jun.ecommerce.exceptions.ResourceExceptionHandler;
import com.jun.ecommerce.services.ProductService;
import com.jun.ecommerce.services.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
//@WebMvcTest(ProductController.class)
public class ProductControllerTest {

	@Autowired
	private MockMvc mvc;

	@Mock
	private ProductService service;

	@InjectMocks
	private ProductController controller;

	@BeforeEach
	public void setUp() {
		mvc = MockMvcBuilders.standaloneSetup(controller)
				.setControllerAdvice(ResourceExceptionHandler.class)
				.build();
	}

	@Test
	public void whenGetAllRequested_shoudReturnAllProduts() throws Exception {
		List<ProductsById> products = List.of(new ProductsById());
		products.get(0).setName("Chair");
		products.get(0).setCategory("Chair");

		when(service.getAllProducts()).thenReturn(products);

		mvc.perform(get("/products")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.size()", is(1)))
				.andExpect(jsonPath("$[0].name").value("Chair"));
	}

	@Test
	public void whenFindByID_shouldReturnTheProduct() throws Exception {
		ProductsById product = new ProductsById();
		UUID randomUUID = UUID.randomUUID();
		product.setId(randomUUID);
		when(service.getProductById(randomUUID)).thenReturn(product);

		mvc.perform(get("/product/{id}", randomUUID))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	public void whenAddNewProduct_shouldReturnTheProduct() throws Exception {
		ProductsById product = new ProductsById();
		product.setName("desk");
		when(service.addProduct(product)).thenReturn(product);

		mvc.perform(post("/product")
				.contentType(APPLICATION_JSON)
				.content(mapJsonToString(product)))
			.andDo(print())
			.andExpect(status().isAccepted())
			.andExpect(jsonPath("$.name").value("desk"));
	}
	
	@Test
	public void whenDeleteProduct_shouldReturnDeletedProduct() throws Exception {
		ProductsById product = new ProductsById();
		UUID randomUUID = UUID.randomUUID();
		product.setId(randomUUID);
		product.setName("desk");

		doNothing().when(service).deleteProduct(product.getId());
		
		mvc.perform(delete("/product/{id}", randomUUID))
			.andDo(print())
			.andExpect(status().isOk());
		verify(service, times(1)).deleteProduct(randomUUID);
	}

	public static String mapJsonToString(final ProductsById product) {
		try {
			return new ObjectMapper().writeValueAsString(product);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

}
