package com.ngonzano.springcloud.msvc.users.controllers;

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

import com.ngonzano.springcloud.msvc.users.dto.UserUpdateRequestDto;
import com.ngonzano.springcloud.msvc.users.entities.User;
import com.ngonzano.springcloud.msvc.users.services.UserService;

@RestController
public class UserController {

	private final UserService service;

	public UserController(UserService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		return service.findById(id)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<?> save(@RequestBody User user) {
		return Optional.of(user)
				.map(service::save)
				.map(saved -> ResponseEntity.status(HttpStatus.CREATED).body(saved))
				.orElseGet(() -> ResponseEntity.badRequest().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UserUpdateRequestDto request) {
		return service.update(id, request)
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
