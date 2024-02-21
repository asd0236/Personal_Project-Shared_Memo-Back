package com.shareMemo.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ShareMemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShareMemoApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedMethods("*")                    // GET, POST, PUT 등 모든 메서드 허용
						.allowedOriginPatterns("*")
						.allowedHeaders("*")
						.allowCredentials(true);
			}
		};
	}
}
