package com.jun.ecommerce;

import java.nio.file.Path;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import com.jun.ecommerce.configuration.DataStaxAstraProperties;

@SpringBootApplication(scanBasePackages = "com.jun")
@EnableConfigurationProperties(DataStaxAstraProperties.class)
@EnableCassandraRepositories(basePackages = "com.jun.ecommerce.data")
public class ECommerceShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceShopApplication.class, args);
	}
	
	@Bean
	public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties properties) {
		Path bundle = properties.getSecureConnectBundle().toPath();
		return builder-> builder.withCloudSecureConnectBundle(bundle);
	}
	
}
