package com.ngonzano.springcloud.msvc.users.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import com.ngonzano.springcloud.msvc.users.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {

	@EntityGraph(attributePaths = "roles")
	User findByUsername(String username);
}
