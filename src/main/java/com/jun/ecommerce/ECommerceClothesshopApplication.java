package com.jun.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.jun.ecommerce.services.UserService;

@SpringBootApplication(scanBasePackages = "com.jun")
public class ECommerceClothesshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceClothesshopApplication.class, args);
	}
}
