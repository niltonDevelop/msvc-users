package com.ngonzano.springcloud.msvc.users.api.v2;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ngonzano.springcloud.msvc.users.api.v2.mapper.UserV2Mapper;
import com.ngonzano.springcloud.msvc.users.dto.UserUpdateRequestDto;
import com.ngonzano.springcloud.msvc.users.entities.User;
import com.ngonzano.springcloud.msvc.users.services.UserService;

@RestController
public class UserV2Controller {

	private final UserService service;

	public UserV2Controller(UserService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(
				service.findAll().stream()
						.map(UserV2Mapper::toResponse)
						.toList());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		return service.findById(id)
				.map(UserV2Mapper::toResponse)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<?> save(@RequestBody User user) {
		return Optional.of(user)
				.map(service::save)
				.map(UserV2Mapper::toResponse)
				.map(saved -> ResponseEntity.status(HttpStatus.CREATED).body(saved))
				.orElseGet(() -> ResponseEntity.badRequest().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UserUpdateRequestDto request) {
		return service.update(id, request)
				.map(UserV2Mapper::toResponse)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		return Optional.of(service.deleteById(id))
				.filter(Boolean::booleanValue)
				.map(deleted -> ResponseEntity.noContent().build())
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

}
