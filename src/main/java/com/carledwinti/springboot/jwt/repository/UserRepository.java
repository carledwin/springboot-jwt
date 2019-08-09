package com.carledwinti.springboot.jwt.repository;



import org.springframework.data.repository.CrudRepository;

import com.carledwinti.springboot.jwt.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByUsername(String username);
}
