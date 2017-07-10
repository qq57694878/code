package com.youi.business.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

public class CorsConfigurerAdapter extends WebMvcConfigurerAdapter {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**/*").allowedOrigins("*");
	}
}