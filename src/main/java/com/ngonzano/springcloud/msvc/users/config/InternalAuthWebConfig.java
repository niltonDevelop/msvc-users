package com.ngonzano.springcloud.msvc.users.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InternalAuthWebConfig implements WebMvcConfigurer {

	private final InternalAuthInterceptor internalAuthInterceptor;

	public InternalAuthWebConfig(InternalAuthInterceptor internalAuthInterceptor) {
		this.internalAuthInterceptor = internalAuthInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(internalAuthInterceptor)
				.addPathPatterns("/internal/**");
	}
}
