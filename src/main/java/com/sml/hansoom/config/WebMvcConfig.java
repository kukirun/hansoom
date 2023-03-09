package com.sml.hansoom.config;

import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 스프링 빈으로 등록
public class WebMvcConfig implements WebMvcConfigurer{
	
	private final long MAX_AGE_SECS = 3600;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// TODO Auto-generated method stub
		//모든 경로에 대해
		registry.addMapping("/**")
		//origin이 http>localhost:3000에 대해
		.allowedOrigins("http://localhost:3000",
				"http://prod-todo-backend3.ap-northeast-2.elasticbeanstalk.com")
		//get post put patch delete options 를 허용
		.allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS")
		//모든 헤더와 인증에 관한 정보(ccredentials)도 허용
		.allowedHeaders("*")
		.allowCredentials(true)
		.maxAge(MAX_AGE_SECS);
	}
	
	
	
}
