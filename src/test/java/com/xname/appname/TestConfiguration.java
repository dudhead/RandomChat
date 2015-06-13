package com.xname.appname;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableWebSecurity
public class TestConfiguration {

	/*
	 * Define beans required for testing over here
	 */
}
