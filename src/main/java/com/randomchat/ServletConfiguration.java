package com.randomchat;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/*
 * Entry point for the applicaion in a Servlet 3.0 environment
 * 
 * Tomcat 7 or similar containers will load this class, which will
 * eventually load Main and from there everything else is loaded.
 */
public class ServletConfiguration extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Main.class);
	}
}