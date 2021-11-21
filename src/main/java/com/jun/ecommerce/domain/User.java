package com.jun.ecommerce.domain;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.Data;

@Data
@Table("user_by_id")
public class User {
	
	@Id
	@PrimaryKeyColumn(name = "user_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	@CassandraType(type = Name.UUID)
	private UUID id;
	
	@Column("user_first_name")
	@NotNull(message = "Name cannot be empty")
	@CassandraType(type = Name.TEXT)
	private String firstName;
	
	@Column("user_last_name")
	@NotNull(message = "Name cannot be empty")
	@CassandraType(type = Name.TEXT)
	private String lastName;
	
	@Column("user_username")
	@NotNull(message = "Username cannot be empty")
	@CassandraType(type = Name.TEXT)
	private String username;
	
	@Column("user_password")
	@NotNull(message = "Password cannot be empty")
	@CassandraType(type = Name.TEXT)
	private String password;
	
	@Email
	@NotNull(message = "Email cannot be empty")
	@Column("user_email")
	@CassandraType(type = Name.TEXT)
	private String email;
	
	@Column("user_orders")
	@CassandraType(type = Name.LIST, typeArguments = Name.UUID)
	private List<UUID> orderIds;
}
