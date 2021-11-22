package com.jun.ecommerce.domain;

import java.util.UUID;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;

import lombok.Data;

@Data
@Table("cart_by_user")
public class Cart {

	public Cart() {
		this.cartId = UUID.randomUUID();
	}

	public Cart(String userId, String email) {
		this.cartId = UUID.randomUUID();
		this.userId = userId;
		this.email = email;
	}
	
	@PrimaryKeyColumn(name = "cart_user_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	@CassandraType(type = Name.TEXT)
	private String userId;
	
	@PrimaryKeyColumn(name = "cart_id", type = PrimaryKeyType.CLUSTERED)
	@CassandraType(type = Name.UUID)
	private UUID cartId;
	
	@Column("cart_user_email_id")
	@CassandraType(type = Name.TEXT)
	private String email;
}
