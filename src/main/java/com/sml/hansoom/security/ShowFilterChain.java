package com.sml.hansoom.security;

import java.io.IOException;



import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Order(value = 0)
public class ShowFilterChain extends OncePerRequestFilter {

	@Autowired
	private ApplicationContext applicationContext;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//		try {
//			applicationContext = BeanGetter.getContext();
//			log.info("★Show FilterChain★"+"   "+request.getRequestURL());
//			Map<String, Filter> filters = applicationContext.getBeansOfType(Filter.class);
//			
//			for (Map.Entry<String, Filter> entry : filters.entrySet()) {
//			    String filterName = entry.getKey();
//			    Filter filter = entry.getValue();
//			    System.out.println("Filter Name: " + filterName);
//			    System.out.println("Filter Class: " + filter.getClass().getName());
//			    System.out.println("--------------------");
//			}
//			
//		} catch(Exception ex) {
//			log.info(ex.getMessage());
//		}
		filterChain.doFilter(request, response);
	}

}




 