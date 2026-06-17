package com.ngonzano.springcloud.msvc.users.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	private static final String API_V1_PACKAGE = ".api.v1";
	private static final String API_V2_PACKAGE = ".api.v2";

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		configurer.addPathPrefix("/v1", clazz -> clazz.getPackageName().endsWith(API_V1_PACKAGE));
		configurer.addPathPrefix("/v2", clazz -> clazz.getPackageName().endsWith(API_V2_PACKAGE));
	}

}
