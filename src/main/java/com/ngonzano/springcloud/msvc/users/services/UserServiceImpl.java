package com.ngonzano.springcloud.msvc.users.services;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ngonzano.springcloud.msvc.users.dto.UserUpdateRequestDto;
import com.ngonzano.springcloud.msvc.users.entities.Role;
import com.ngonzano.springcloud.msvc.users.entities.User;
import com.ngonzano.springcloud.msvc.users.exception.RoleNotFoundException;
import com.ngonzano.springcloud.msvc.users.repositories.RoleRepository;
import com.ngonzano.springcloud.msvc.users.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findAll() {
		return StreamSupport.stream(userRepository.findAll().spliterator(), false)
				.toList();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

	@Override
	@Transactional
	public User save(User user) {
		return Optional.of(user)
				.map(this::prepareForSave)
				.map(userRepository::save)
				.orElseThrow();
	}

	@Override
	@Transactional
	public Optional<User> update(Long id, UserUpdateRequestDto request) {
		return userRepository.findById(id)
				.map(user -> applyUpdate(user, request))
				.map(userRepository::save);
	}

	@Override
	@Transactional
	public Boolean deleteById(Long id) {
		return Optional.ofNullable(id)
				.filter(userRepository::existsById)
				.map(existingId -> {
					userRepository.deleteById(existingId);
					return true;
				})
				.orElse(false);
	}

	@Override
	public List<Role> getRoles(User user) {
		return getRolesByAdmin(user.getIsAdmin());
	}

	private User prepareForSave(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setEnabled(Optional.ofNullable(user.getEnabled()).orElse(true));
		user.setRoles(getRoles(user));
		return user;
	}

	private User applyUpdate(User user, UserUpdateRequestDto request) {
		applyIfPresent(request.getUsername(), user::setUsername);
		applyIfPresent(request.getPassword(), password -> user.setPassword(passwordEncoder.encode(password)));
		applyIfPresent(request.getEmail(), user::setEmail);
		applyIfPresent(request.getEnabled(), user::setEnabled);
		Optional.ofNullable(request.getIsAdmin())
				.map(this::getRolesByAdmin)
				.ifPresent(user::setRoles);
		return user;
	}

	private List<Role> getRolesByAdmin(Boolean isAdmin) {
		return roleNamesFor(isAdmin).stream()
				.map(this::findRequiredRole)
				.toList();
	}

	private List<String> roleNamesFor(Boolean isAdmin) {
		return Boolean.TRUE.equals(isAdmin)
				? List.of(Role.ROLE_USER, Role.ROLE_ADMIN)
				: List.of(Role.ROLE_USER);
	}

	private Role findRequiredRole(String roleName) {
		return roleRepository.findByName(roleName)
				.orElseThrow(() -> new RoleNotFoundException(roleName));
	}

	private <T> void applyIfPresent(T value, Consumer<T> consumer) {
		Optional.ofNullable(value).ifPresent(consumer);
	}

}
