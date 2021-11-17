package com.jun.ecommerce.domain;

import javax.persistence.Id;

import lombok.Data;

@Data
public class Product {
	
	@Id
	private Long id;
	private String name;
	private String brand;
	private double price;

	public Product() {	}
	public Product(String name, String brand, double price) {
		this.name = name;
		this.brand = brand;
		this.price = price;
	}
}
