package com.ngonzano.springcloud.msvc.users.api.internal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ngonzano.springcloud.msvc.users.dto.UserAuthResponseDto;
import com.ngonzano.springcloud.msvc.users.services.UserService;

@RestController
@RequestMapping("/internal/auth")
public class InternalAuthController {

	private final UserService userService;

	public InternalAuthController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/users/{username}")
	public ResponseEntity<UserAuthResponseDto> findByUsername(@PathVariable String username) {
		return userService.findByUsernameForAuth(username)
				.map(user -> new UserAuthResponseDto(
						user.getUsername(),
						user.getPassword(),
						Boolean.TRUE.equals(user.getEnabled()),
						user.getRoles() == null ? java.util.List.of() : user.getRoles().stream()
								.map(role -> role.getName())
								.toList()))
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
}
