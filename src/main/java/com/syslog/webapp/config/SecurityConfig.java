package com.syslog.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.syslog.webapp.filter.SyslogAuthenticationFailureHandler;
import com.syslog.webapp.filter.SyslogUsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/index","/home").hasRole("LOGIN")
		.antMatchers("/user/query","/user/queryById","/user/queryByUsername")
			.hasAnyRole("ADD_USER","UPDATE_USER","DELETE_USER","QUERY_USER")
		.antMatchers("/user/insert").hasAnyRole("ADD_USER")
		.antMatchers("/user/update").hasAnyRole("UPDATE_USER")
		.and().logout().invalidateHttpSession(true)
		.and().csrf().disable()
		.addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter.class)
		.formLogin().loginPage("/login").permitAll();
	}
	
	@Bean("usernamePasswordAuthenticationFilter")
	public UsernamePasswordAuthenticationFilter loginFilter() throws Exception {
		UsernamePasswordAuthenticationFilter loginFilter = new SyslogUsernamePasswordAuthenticationFilter();
		loginFilter.setAuthenticationManager(authenticationManagerBean());
		loginFilter.setAuthenticationFailureHandler(AuthenticationFailureHandler());
		return loginFilter;
	}

	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public AuthenticationFailureHandler AuthenticationFailureHandler() {
		return new SyslogAuthenticationFailureHandler();
	}
}
