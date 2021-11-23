package com.jun.ecommerce.domain;

import java.util.UUID;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;

import lombok.Data;

@Data
@Table("carts_by_user")
public class Cart {

	public Cart() {
		this.cartId = UUID.randomUUID();
	}

	public Cart(String userId, UUID cartId, boolean active, String address) {
		this.userId = userId;
		this.cartId = cartId;
		this.active = active;
		this.address = address;
	}

	@PrimaryKeyColumn(name = "cart_user_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	@CassandraType(type = Name.TEXT)
	private String userId;

	@PrimaryKeyColumn(name = "cart_id", type = PrimaryKeyType.CLUSTERED)
	@CassandraType(type = Name.UUID)
	private UUID cartId;

	@Column("user_cart_active")
	@CassandraType(type = Name.BOOLEAN)
	private boolean active;

	@Column("user_address")
	@CassandraType(type = Name.TEXT)
	private String address;

}
