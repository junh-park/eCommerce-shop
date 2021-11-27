package com.jun.ecommerce.domain;

import java.util.UUID;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import com.datastax.driver.core.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table("products_by_cart")
public class ProductsByCart {

	public ProductsByCart(UUID cartId, UUID productId, String name, String category,
			String manufacturer, double price, String desc, String imageUrl, int quantity, double subtotal) {
		this.cartId = cartId;
		this.productId = productId;
		this.name = name;
		this.category = category;
		this.manufacturer = manufacturer;
		this.price = price;
		this.desc = desc;
		this.imageUrl = imageUrl;
		this.quantity = quantity;
		this.subtotal = subtotal;
	}

	@PrimaryKeyColumn(name = "cart_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	@CassandraType(type = Name.UUID)
	private UUID cartId;
	
	@PrimaryKeyColumn(name = "product_id", type = PrimaryKeyType.CLUSTERED)
	@CassandraType(type = Name.UUID)
	private UUID productId;

	@Column("product_name")
	@CassandraType(type = Name.TEXT)
	private String name;
	
	@Column("product_category")
	@CassandraType(type = Name.TEXT)
	private String category;
	
	@Column("manufacturer")
	@CassandraType(type = Name.TEXT)
	private String manufacturer;
	
	@Column("product_price")
	@CassandraType(type = Name.DOUBLE)
	private double price;
	
	@Column("product_description")
	@CassandraType(type = Name.TEXT)
	private String desc;
	
	@Column("product_image_url")
	@CassandraType(type = Name.TEXT)
	private String imageUrl;
	
	@Column("quantity")
	@CassandraType(type = Name.INT)
	private int quantity;
	
	@Column("subtotal")
	@CassandraType(type = Name.DOUBLE)
	private double subtotal;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductsByCart other = (ProductsByCart) obj;
		if (cartId == null) {
			if (other.cartId != null)
				return false;
		} else if (!cartId.equals(other.cartId))
			return false;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cartId == null) ? 0 : cartId.hashCode());
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		return result;
	}
}
