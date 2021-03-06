package com.jun.ecommerce.data;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.jun.ecommerce.domain.User;

public interface UserRepository extends CassandraRepository<User, UUID>{
}
