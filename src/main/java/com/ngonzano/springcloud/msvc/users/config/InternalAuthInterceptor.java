package com.ngonzano.springcloud.msvc.users.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class InternalAuthInterceptor implements HandlerInterceptor {

	private static final String INTERNAL_TOKEN_HEADER = "X-Internal-Token";

	private final String internalToken;

	public InternalAuthInterceptor(@Value("${oauth.internal-token}") String internalToken) {
		this.internalToken = internalToken;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = request.getHeader(INTERNAL_TOKEN_HEADER);
		if (internalToken.equals(token)) {
			return true;
		}
		response.sendError(HttpStatus.UNAUTHORIZED.value());
		return false;
	}
}
