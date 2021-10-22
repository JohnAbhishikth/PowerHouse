//package com.lti.solvathon.jwt.config;
//
//import java.io.IOException;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import io.jsonwebtoken.ExpiredJwtException;
//
//@Component
//public class JwtRequestFilter extends OncePerRequestFilter {
//
//	@Autowired
//	private JwtTokenUtil jwtTokenUtil;
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//			throws ServletException, IOException {
//
//		final String requestTokenHeader = request.getHeader("Authorization");
//
//		String username = null;
//		String jwtToken = null;
//
//		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
//
//			jwtToken = requestTokenHeader.substring(7);
//			try {
//				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
//			} catch (IllegalArgumentException e) {
//				logger.error("Unable to get JWT Token");
//			} catch (ExpiredJwtException e) {
//				logger.error("JWT Token has expired");
//			}
//
//		} else {
//			logger.warn("JWT Token not begin with Bearer string");
//		}
//
//		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//			if (jwtTokenUtil.ValidateToken(jwtToken, username)) {
//
//				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//						username, null, null);
//				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//			}
//
//		}
//		chain.doFilter(request, response);
//	}
//}
