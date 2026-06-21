package com.ngonzano.springcloud.msvc.users.dto;

import java.util.List;

public record UserAuthResponseDto(
		String username,
		String password,
		boolean enabled,
		List<String> roles) {
}
