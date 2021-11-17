package com.jun.ecommerce.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jun.ecommerce.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
