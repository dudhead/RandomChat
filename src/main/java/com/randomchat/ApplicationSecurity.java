package com.randomchat;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.ebaysf.web.cors.CORSFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

/*
 * Configure URL based access control via roles and permissions
 * Also controls CORS security headers
 */
@Service
class ApplicationSecurity extends WebSecurityConfigurerAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationSecurity.class);
	

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*
		 * Disable CSRF
		 * Because we are not using cookies at all for authentication, 
		 * ..and because we are intentionally allowing cross-domain requests,
		 * this is a safe thing to do.
		 */
		http.csrf().disable();
		
		
		/*
		 * Disable session's completely, and go stateless
		 * ... and instead use our custom token repository 
		 * to manage security between requests
		 * 
		 */
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
//		http.headers().cacheControl();
//		
//		http.addFilterBefore(new OncePerRequestFilter() {
//			
//			@Override
//			protected void doFilterInternal(HttpServletRequest request,
//					HttpServletResponse response, FilterChain chain)
//					throws ServletException, IOException {
//				chain.doFilter(request, response);
//				HttpServletRequest req = (HttpServletRequest) request;
//				if (req.getRequestURI().contains("charts")) {
//					HttpServletResponse resp = (HttpServletResponse) response;
//					resp.addHeader("Cache-Control", "maxage=3600,public");
//				}
//				
//			}
//		}, SecurityContextPersistenceFilter.class);
		
		
		
		http.authorizeRequests()
			.antMatchers("/","/api/**","*/send").permitAll()
			.anyRequest().fullyAuthenticated();
		
		http
			.formLogin()
				.loginPage("/login").failureUrl("/login?error").permitAll()
			.and()
				.logout().logoutRequestMatcher(
					new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");
		//Use Below for Basic authentication
		http.httpBasic();
	}


}

class CsrfSecurityRequestMatcher implements RequestMatcher {

	private Pattern allowedMethods = Pattern
			.compile("^(GET|HEAD|TRACE|OPTIONS)$");
	private RegexRequestMatcher unprotectedMatcher = new RegexRequestMatcher(
			"/api/**", null, true);

	@Override
	public boolean matches(HttpServletRequest request) {
		if (allowedMethods.matcher(request.getMethod()).matches()) {
			return false;
		}
		return !(unprotectedMatcher.matches(request));
	}
}



class MapBasedFilterConfig implements FilterConfig {

	private final Map<String, String> initParams;
	private final String filterName;
	
	public MapBasedFilterConfig(String filterName, Map<String, String> initParams) {
		this.filterName = filterName;
		if (initParams != null && !initParams.isEmpty()) {
			this.initParams = Collections.unmodifiableMap(initParams);
		}
		else {
			this.initParams = Collections.emptyMap();
		}
	}
	
	@Override
	public String getFilterName() {
		return this.filterName;
	}

	/*
	 * We know that CORSFilter doesn't really use the ServletContext
	 * .. except for logging
	 * We don't really care about loggin in CORSFilter, so to hell with it
	 */
	@Override
	public ServletContext getServletContext() {
		return null;
	}

	@Override
	public String getInitParameter(String name) {
		return this.initParams.get(name);
	}

	@Override
	public Enumeration<String> getInitParameterNames() {
		return Collections.enumeration(this.initParams.keySet());
	}
	
}