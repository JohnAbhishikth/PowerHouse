//package com.lti.solvathon.jwt.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import com.lti.solvathon.jwt.config.JwtRequestFilter;
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//	@Autowired
//	JwtRequestFilter jwtRequestFilter;
//	
//	@Override
//	public void configure(HttpSecurity http) throws Exception {
//		http.cors().and().csrf().disable();
//		
//		http.anonymous()
//				.and()
//					.authorizeRequests().antMatchers("/auth/login").permitAll()
//				.and()
//					.authorizeRequests().anyRequest().authenticated()
//				.and()
//					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//			http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//	}
//
//}
