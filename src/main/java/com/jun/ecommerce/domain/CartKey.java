package com.jun.ecommerce.domain;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import lombok.Data;

import org.springframework.data.cassandra.core.mapping.CassandraType.Name;

@Data
@PrimaryKeyClass
public class CartKey {

	public CartKey() {
		this.cartId = UUID.randomUUID();
	}

	public CartKey(String userId, String cartId) {
		this.cartId = UUID.randomUUID();
		this.userId = userId;
	}
	
	@PrimaryKeyColumn(name = "cart_user_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	@CassandraType(type = Name.TEXT)
	private String userId;
	
	@PrimaryKeyColumn(name = "cart_id", type = PrimaryKeyType.PARTITIONED)
	@CassandraType(type = Name.UUID)
	private UUID cartId;
	
}
