package com.ngonzano.springcloud.msvc.users.api.v2.dto;

import java.util.List;

import com.ngonzano.springcloud.msvc.users.entities.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserV2ResponseDto {

	private Long id;
	private String username;
	private Boolean enabled;
	private String email;
	private List<Role> roles;
	private String apiVersion = "v2";

}
