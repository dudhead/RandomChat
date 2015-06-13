package com.randomchat;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.stereotype.Service;

/*
 * Configure authentication rules in this class
 * Refer to Spring Security's documentation...
 * ... but more importantly, play around with auto-completion in IDE
 */
@Service
class AuthenticationSecurity extends
		GlobalAuthenticationConfigurerAdapter {

	
	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
				.withUser("superadmin").password("superadmin").roles("SUPERADMIN");
	}
}