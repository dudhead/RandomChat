package com.randomchat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/*
 * Enable Spring web security. See ApplicationSecurity.java and AuthenticationSecurity.java 
 */
@EnableWebSecurity

/*
 * This is a spring configuration bean. Spring will automatically load beans
 * defined in this class
 */
@Configuration

/*
 * Enable spring boot's auto-configuration. 
 * This automatically detects libraries in the classpath and sets up various beans 
 */
@EnableAutoConfiguration

/*
 * Scan for @Service, @Component, @Bean etc under the current package
 * .. and automatically manage them, dependency inject them etc.
 */
@ComponentScan

@EnableWebMvc

/*
 * This is the main entry point of the application. 
 * Running this program starts a tomcat internally. 
 * 
 * When deployed as a WAR file, ServletConfiguration.java
 * is the entry point. But that class simply delegates to Main
 * as well to start the application.
 */
public class Main extends WebMvcConfigurerAdapter{

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	
	public static void main(String args[]) {
		SpringApplication.run(Main.class, args);
		LOGGER.info("Started Application");
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
	}
}
