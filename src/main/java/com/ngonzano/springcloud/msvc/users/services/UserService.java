package com.ngonzano.springcloud.msvc.users.services;

import java.util.List;
import java.util.Optional;

import com.ngonzano.springcloud.msvc.users.dto.UserUpdateRequestDto;
import com.ngonzano.springcloud.msvc.users.entities.Role;
import com.ngonzano.springcloud.msvc.users.entities.User;

public interface UserService {

	List<User> findAll();

	Optional<User> findById(Long id);

	Optional<User> findByUsernameForAuth(String username);

	User save(User user);

	Optional<User> update(Long id, UserUpdateRequestDto request);

	Boolean deleteById(Long id);

	List<Role> getRoles(User user);

}
