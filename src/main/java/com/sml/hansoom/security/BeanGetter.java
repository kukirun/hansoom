package com.sml.hansoom.security;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class BeanGetter {

	static WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
	
	public static ApplicationContext getContext() {
		ApplicationContext context = ApplicationContextProvider.getApplicationContext();
		return context;
	}
    
	public static Object getBean( String beanName ) {
		ApplicationContext context = ApplicationContextProvider.getApplicationContext();
		return context.getBean( beanName );
	}

}
