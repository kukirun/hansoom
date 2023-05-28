package com.sml.hansoom.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.DisableEncodeUrlFilter;

import com.sml.hansoom.security.JwtAuthenticationFilter;
import com.sml.hansoom.security.ShowFilterChain;

import jakarta.servlet.Filter;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity(debug = false)
@Slf4j
public class WebSecurityConfig {
	
//	@Autowired
//	JwtAuthenticationFilter jwtAuthenticationFilter;
//	JwtAuthenticationFilter jwtAuthenticationFilter  = new JwtAuthenticationFilter();
	@Autowired
	ShowFilterChain showFilterChain;
	@Autowired
	private ApplicationContext applicationContext;

	
    @Bean
    public FilterRegistrationBean<Filter> jwtFilterRegistration() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtAuthenticationFilter());
        registrationBean.addUrlPatterns("/","/error","/test/*"); // 필터가 적용될 URL 패턴
        registrationBean.setOrder(0); // 필터 실행 순서
        return registrationBean;
    }
//    
//    @Bean
//    public FilterRegistrationBean<Filter> showFilterRegistration() {
//        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new ShowFilterChain());
//        registrationBean.addUrlPatterns("/"); // 필터가 적용될 URL 패턴
//        registrationBean.setOrder(1); // 필터 실행 순서
//        return registrationBean;
//    }

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		log.info("secure filter");
		
		http.addFilterBefore(showFilterChain,DisableEncodeUrlFilter.class);
		
		http
		.cors()//webmvcconfig에서 이미 설정했으므로 기본 cors
		.and()
		.csrf()//csrf는 현재 사용하지 않으니 disable
			.disable()
		.httpBasic() //token을 사용하므로 basic인증 dis
			.disable()
		.sessionManagement()//session 기반이 아님을 선언
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authorizeHttpRequests()// / 와 /auth/** 경로는 인증 안해도 됨b
			.requestMatchers("/", "/api/auth/*","/error","/test/*").permitAll()
		.anyRequest() // /와 /auth/** 이외의 경로는 인증 필요
		.authenticated();
		


		return http.build();
	}
	
}
