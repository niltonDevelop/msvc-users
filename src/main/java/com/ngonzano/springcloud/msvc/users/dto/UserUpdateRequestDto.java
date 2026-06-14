package com.ngonzano.springcloud.msvc.users.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequestDto {

	private String username;
	private String password;
	private String email;
	private Boolean enabled;
	private Boolean isAdmin;

}
