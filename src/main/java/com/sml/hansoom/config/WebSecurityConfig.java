package com.sml.hansoom.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

import com.sml.hansoom.security.JwtAuthenticationFilter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig {
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		log.info("secure filter");
		http.cors()//webmvcconfig에서 이미 설정했으므로 기본 cors
		.and()
		.csrf()//csrf는 현재 사용하지 않으니 disable
			.disable()
		.httpBasic() //token을 사용하므로 basic인증 dis
			.disable()
		.sessionManagement()//session 기반이 아님을 선언
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authorizeHttpRequests()// / 와 /auth/** 경로는 인증 안해도 됨b
			.requestMatchers("/", "/auth/**","/error").permitAll()
		.anyRequest() // /와 /auth/** 이외의 경로는 인증 필요
			.authenticated();
		
		http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);
		
		return http.build();
	}
	
}
