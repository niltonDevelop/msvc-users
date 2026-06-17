package com.ngonzano.springcloud.msvc.users.api.v2.mapper;

import com.ngonzano.springcloud.msvc.users.api.v2.dto.UserV2ResponseDto;
import com.ngonzano.springcloud.msvc.users.entities.User;

public final class UserV2Mapper {

	private UserV2Mapper() {
	}

	public static UserV2ResponseDto toResponse(User user) {
		UserV2ResponseDto response = new UserV2ResponseDto();
		response.setId(user.getId());
		response.setUsername(user.getUsername());
		response.setEnabled(user.getEnabled());
		response.setEmail(user.getEmail());
		response.setRoles(user.getRoles());
		return response;
	}

}
